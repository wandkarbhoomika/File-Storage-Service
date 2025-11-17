# File-Storage-Service

A lightweight file storage and retrieval system built using **Spring Boot**.  
It allows users to upload files, stores them safely in a local folder, and saves metadata  
(file name, path, size, type, owner) in **MySQL**.

Supports secure file download, automatic download link generation,  
and clean delete operations with disk + DB validation.

This project is a mini **AWS S3–style file storage backend** built using Spring Boot, MySQL, and local filesystem.  
It provides REST endpoints to upload, download, list, and delete files.

---

## 🚀 Key Features

- File Upload API using `MultipartFile`
- Saves files to a secure local directory
- Stores metadata (filename, path, size, type, timestamp, owner) in MySQL
- Download API returns file bytes and forces browser download
- Generates dynamic download URL for easy file access
- Delete API checks both disk + DB before removing data
- Clean exception handling and clear JSON responses
- Easily switchable to AWS S3 without changing controller logic

---

## 🛠 Tech Stack

- Spring Boot  
- MySQL (metadata storage)  
- Local filesystem (file storage)  
- JPA / Hibernate 
- Lombok
- Postman (API testing)  

---
