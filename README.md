# File Management

The File Management is a Spring Boot application designed to provide basic file management functionalities such as storing, downloading, and deleting files from a specified location on the server.

---

![Project Media](./media/file-management-gif.gif)

---

## Setup Project

#### To clone and start this project, you can follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/JonasDias10/file-management.git
   ```

2. Navigate into the project directory:

   ```bash
   cd file-management/file-management
   ```

3. Run the project:
   ```bash
   mvn spring-boot:run
   ```

---

## Endpoints

| Method | Endpoint                          | Description                                                   |
| ------ | --------------------------------- | ------------------------------------------------------------- |
| POST   | /api/v1/files/upload              | Uploads a file to the server.                                 |
| GET    | /api/v1/files/download/{fileName} | Downloads a file from the server by specifying its filename.  |
| DELETE | /api/v1/files/delete/{fileName}   | Deletes a file from the server by specifying its filename.    |
| GET    | /api/v1/files/all                 | Retrieves a list of all files currently stored on the server. |

