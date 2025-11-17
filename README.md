# File-Storage-Service

A lightweight file storage and retrieval system built using **Spring Boot**.  
It allows users to upload files, stores them safely in a local folder and saves metadata(file name, path, size, type, owner) in **MySQL**.
Supports secure file download, automatic download link generation and clean delete operations with disk + DB validation.

This project is a mini **AWS S3–style file storage backend** built using Spring Boot, MySQL, and local filesystem.  
It provides REST endpoints to upload, download, list and delete files.

---

## 🚀 Key Features

- File Upload API using `MultipartFile` (MultipartFile is a Spring Boot interface that represents an uploaded file in a HTTP request.
Used when submitting files from Postman or a form)
- Saves files to a secure local directory
- Stores metadata (filename, path, size, type, timestamp, owner) in MySQL
- Download API returns file bytes 
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

### API Endpoints & CURL commands

### 1️ Upload File

POST /files/upload
- Postman (form-data)
- Key: file → Select File
- Key: owner → enter owner name

CURL
curl --location --request POST 'http://localhost:8000/files/upload' \
--form 'file=@"C:/Users/Bhoomika/Downloads/deloitte.pdf"' \
--form 'owner=bhoomi'

Response 
{
    "id": 14,
    "filename": "12",
    "filepath": "D:/PROJECT/eclipse-workspace_P2/file_to_upload/12",
    "size": 323909,
    "contentType": "application/octet-stream",
    "owner": "bhoomi",
    "createdAt": "2025-11-17T22:17:36.2262321"
}
<img width="1367" height="398" alt="image" src="https://github.com/user-attachments/assets/100e98fc-e66d-42ff-b830-a9e7b5738960" />



