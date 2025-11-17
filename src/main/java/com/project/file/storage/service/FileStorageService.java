package com.project.file.storage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.file.storage.entity.FileMetadata;
import com.project.file.storage.repository.FileMetadataRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileStorageService {

	private final FileMetadataRepository repository;
	private final String uploadDir;

	public FileStorageService(FileMetadataRepository repository, @Value("${file.upload-dir}") String uploadDir) {
		this.repository = repository;
		this.uploadDir = uploadDir;

		new File(uploadDir).mkdirs(); // folder exists
	}

	// Save file to disk and metadata to DB
	public FileMetadata storeFile(MultipartFile file, String owner) throws IOException {
		String filePath = uploadDir + "/" + file.getOriginalFilename();

		// Save file
		Files.copy(file.getInputStream(), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);

		// Save metadata
		FileMetadata meta = new FileMetadata();
		meta.setFilename(file.getOriginalFilename());
		meta.setFilepath(filePath);
		meta.setSize(file.getSize());
		meta.setContentType(file.getContentType());
		meta.setOwner(owner);

		return repository.save(meta);
	}

	// Get metadata from DB
	public FileMetadata getMetadata(Long id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
	}

	// Read file from folder
	public byte[] downloadFile(Long id) throws IOException {
		FileMetadata meta = getMetadata(id);
		return Files.readAllBytes(Path.of(meta.getFilepath()));
	}

	public void deleteFile(Long id) throws IOException {
	    // Fetch metadata from DB
	    FileMetadata meta = repository.findById(id)
	            .orElseThrow(() -> new RuntimeException("File not found in database"));

	    Path path = Path.of(meta.getFilepath());

	    //deleting from disk / folder
	    if (Files.exists(path)) {
	        try {
	            Files.delete(path);
	        } catch (IOException e) {
	            throw new IOException("Error deleting file from disk: " + e.getMessage());
	        }
	    } else {
	        // Do NOT throw error —  log warning
	        System.out.println("Warning: File missing on disk: " + path.toString());
	    }

	    // ALWAYS delete DB record to avoid outdated entries
	    repository.deleteById(id);
	}

	public List<FileMetadata> getAllFiles() {
	    return repository.findAll();
	}


}

