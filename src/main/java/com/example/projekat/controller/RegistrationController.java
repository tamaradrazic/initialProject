package com.example.projekat.controller;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projekat.entities.UserEntity;
import com.example.projekat.entities.UserRole;
import com.example.projekat.repository.UserRepository;
import com.example.projekat.repository.UserRoleRepository;

/**
 * RegistrationController The RegistrationController class implements methods for
 * registration of new user.
 * 
 * @author tamara
 * @since 2022-12-30
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	UserRepository ur;

	@Autowired
	UserRoleRepository urr;

	@Autowired
	private JavaMailSender mailSender;

	private static final String SALT = BCrypt.gensalt();

	public boolean registrate(String firstName, String lastName, String userName, String password, String email)
			throws UnsupportedEncodingException, MessagingException {
		System.out.println(SALT);
		try {
			if (ur.findUserEntityByName(userName) == null) {
				UserEntity u = new UserEntity();
				UserRole role = urr.findUserRoleByName("user");
				u.setFirstName(firstName);
				u.setLastName(lastName);
				u.setUsername(userName);
				u.setPassword(BCrypt.hashpw(password, SALT));
				u.setEmail(email);
				u.setActive(false);
				u.setUserRole(role);
				Random random = new Random();
				int num = random.nextInt(999999);
				String code = String.format("%06d", num);
				u.setCode(code);
				u.setStatus(false);
				ur.save(u);
				sendVerificationEmail(u);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method is used to create new user by admin.
	 * 
	 * @param firstName  This is parameter in createUserByAdmin method, and it
	 *                  represents firstname of new user
	 * @param lastName This is parameter in createUserByAdmin method, and it
	 *                  represents lastname of new user
	 * @param password This is parameter in createUserByAdmin method, it
	 *                  represents users password
	 * @param email This parameter represents users email address 
	 * @param role This parameter represents <code>UserRole</code> name that user will have
	 * @return <code>boolean</code> This returns boolean value indicating whether creating of new user is successfully or not 
	 */

	@RequestMapping(value = "/createUserByAdmin", method = RequestMethod.POST)
	public boolean createUserByAdmin(@RequestParam(name = "firstName") String firstName,
			@RequestParam(name = "lastName") String lastName, @RequestParam(name = "username") String userName,
			@RequestParam(name = "password") String password, @RequestParam(name = "email") String email,
			@RequestParam(name = "role") String role) {
		System.out.println(SALT);
		try {
			if (ur.findUserEntityByName(userName) == null) {
				UserEntity u = new UserEntity();
				u.setFirstName(firstName);
				u.setLastName(lastName);
				u.setUsername(userName);
				u.setPassword(BCrypt.hashpw(password, SALT));
				u.setEmail(email);
				UserRole userRole = urr.findUserRoleByName(role);
				u.setUserRole(userRole);
				u.setActive(true);
				u.setStatus(true);
				ur.save(u);

				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void sendVerificationEmail(UserEntity user) throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getEmail();
		String fromAddress = "tamaradrazic9@gmail.com";
		String senderName = "Your company name";
		String subject = "Please verify your registration";
		String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "Your company name.";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", user.getFirstName());
		//String verifyURL = "https://localhost:8443/projekat" + "/registration/verify?code=" + user.getCode();
		String verifyURL = "https://localhost:4200" + "/verify?code=" + user.getCode();
		content = content.replace("[[URL]]", verifyURL);

		helper.setText(content, true);

		mailSender.send(message);
	}

	/**
	 * This method call method <code>registrate</code> and forwards params.
	 * 
	 * @param firstName  This is parameter in processRegister method, and it
	 *                  represents firstname of new user
	 * @param lastName This is parameter in processRegister method, and it
	 *                  represents lastname of new user
	 * @param password This is parameter in processRegister method, it
	 *                  represents users password
	 * @param email This parameter represents users email address 
	 * @return <code>boolean</code>  This returns boolean value indicating whether creating of new user is successfully or not 
	 */
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public boolean processRegister(@RequestParam(name = "firstName") String firstName,
			@RequestParam(name = "lastName") String lastName, @RequestParam(name = "username") String userName,
			@RequestParam(name = "password") String password, @RequestParam(name = "email") String email,
			HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		return registrate(firstName, lastName, userName, password, email);
	}

	public boolean verify(String code) {
		UserEntity user = ur.findUserEntityByCode(code);
		if (user != null) {
			user.setCode(null);
			user.setActive(true);
			user.setStatus(true);
			ur.save(user);
			ur.flush();
			return true;
		}
		return false;
	}

	/**
	 * This method call method <code>verify</code> and forwards parameter code that represents code that is generated when user is registrated. 
	 * Then method <code>verify</code> check if user with that code exists and change active status to true.
	 * 
	 * @param code This parameter represents code that is generated for user when it's registrated.
	 * @return <code>boolean</code>  This returns boolean value indicating whether user with that code exists or not 
	 */
	
	@RequestMapping(value = "/verify")
	public boolean verifyUser(@Param("code") String code) {
		if (verify(code)) {
			System.out.print("Uspesno verifikovan nalog");
			return true;
		} else {
			System.out.print("Greska kod verifikacije");
			return false;
		}
	}
}
