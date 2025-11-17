package com.project.file.storage.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.file.storage.entity.FileMetadata;
import com.project.file.storage.service.FileStorageService;

@RestController
@RequestMapping("/files")
public class FileStorageController {

	private final FileStorageService service;

	public FileStorageController(FileStorageService service) {
		this.service = service;
	}

	@PostMapping("/upload")
	public FileMetadata upload(@RequestParam("file") MultipartFile file, @RequestParam("owner") String owner)
			throws IOException {
		return service.storeFile(file, owner);
	}

	@GetMapping("/download/{id}")
    public Map<String, String> getDownloadUrl(@PathVariable Long id) {
        FileMetadata meta = service.getMetadata(id);

        // Build URL pointing to actual file download endpoint
        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download-file/")
                .path(String.valueOf(meta.getId()))
                .toUriString();

        Map<String, String> response = new HashMap<>();
        response.put("fileName", meta.getFilename());
        response.put("downloadUrl", downloadUrl);

        return response;
    }
	
	@GetMapping("/download-file/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws IOException {
        FileMetadata meta = service.getMetadata(id);
        byte[] data = service.downloadFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + meta.getFilename() + "\"")
                .contentType(MediaType.valueOf(meta.getContentType()))
                .body(data);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteFile(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            service.deleteFile(id);
            response.put("message", "File deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IOException e) {
            response.put("error", "Error deleting file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/list")
    public List<FileMetadata> listAllFiles() {
        return service.getAllFiles();
    }

}
