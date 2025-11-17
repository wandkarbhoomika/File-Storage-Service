package com.project.file.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.file.storage.entity.FileMetadata;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
}