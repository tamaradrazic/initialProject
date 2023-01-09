package com.example.projekat.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.projekat.entities.Patient;
import com.example.projekat.repository.PatientRepository;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

/**
 * CSVUpload The CSVUpload class implements methods for
 * work with CSV files.
 * 
 * @author tamara
 * @since 2022-12-30
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/csv")
public class CSVUpload {

	@Autowired
	PatientRepository pr;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	/**
	 * This method is used to upload existing CSV file and generate list of entities that are then saved in database.
	 * 
	 * @param file  This is parameter in uploadCSVFile method, and it
	 *                  represents CSV file to upload and get data from it to save in database
	 * @return <code>ResponseEntity</code> This returns <code>ResponseEntity</code> that is <code>HTTP status</code> 
	 */
	@RequestMapping("/uploadCSV")
	public ResponseEntity<String> uploadCSVFile(@RequestBody MultipartFile file) {
		
		if(file.isEmpty() || file == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else {
			try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
				CsvToBean<Patient> csv = new CsvToBeanBuilder(reader).withSkipLines(1).withType(Patient.class)
						.withIgnoreLeadingWhiteSpace(true).build();
				
				List<Patient> patients = csv.parse();
				for (Patient patient : patients) {
					if(pr.findPatientsWithSameName(patient.getFirstName(), patient.getLastName())==null) {
						pr.save(patient);
					}
				}
				return new ResponseEntity<>(HttpStatus.OK);
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	public File generateCSV(String table, String fajl) throws IOException{
		String sql = "SELECT * FROM " + table;
	    List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

	    List<List<String>> csvData = new ArrayList<>();
	    for (Map<String, Object> row : rows) {
	        List<String> rowData = new ArrayList<>();
	        for (Map.Entry<String, Object> entry : row.entrySet()) {
	        	Object value = entry.getValue();
	            if (value != null) {
	                rowData.add(value.toString());
	            } else {
	                rowData.add("");
	            }
	        }
	        csvData.add(rowData);
	    }

	    List<String[]> csvDataArrays = new ArrayList<>();
	    for (List<String> rowData : csvData) {
	        csvDataArrays.add(rowData.toArray(new String[rowData.size()]));
	    }

	    File csvFile = new File(fajl);
	    try (Writer writer = new FileWriter(csvFile)) {
	        CSVWriter csvWriter = new CSVWriter(writer);
	        csvWriter.writeAll(csvDataArrays);
	        csvWriter.close();
	    }

	    return csvFile;
	}
	
	/**
	 * This method is used to generate CSV file from data in database and download it.
	 * 
	 * @param table  This is parameter in downloadCSV method, and it
	 *                  represents name of table in database from where we want to get data.
	 * @param fajl 	 This is parameter in downloadCSV method and it represents path to file
	 * @return <code>ResponseEntity<\ByteArrayResource\></code> This returns <code>ResponseEntity</code> that is <code>HTTP status</code> and file is downloaded. 
	 */
	@RequestMapping("/generateCSV")
	public ResponseEntity<ByteArrayResource>  downloadCSV(@RequestParam("table")String table, @RequestParam("fajl")String fajl) throws IOException {
		File csvFile = generateCSV(table, fajl);
	    byte[] data = Files.readAllBytes(csvFile.toPath());
	    ByteArrayResource resource = new ByteArrayResource(data);

	    return ResponseEntity.ok()
	            .contentType(MediaType.parseMediaType("text/csv"))
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + table + ".csv")
	            .body(resource);
	}
}
