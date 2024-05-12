const baseUrl = 'http://localhost:8080/api/v1/files';

const submit = document.getElementById('submit');
submit.addEventListener('click', async (event) => {
      event.preventDefault();

      const file = document.getElementById('file').files[0];
      const formData = new FormData();
      formData.append('file', file);

      const response = await fetch(`${baseUrl}/upload`, {
            method: 'POST',
            body: formData
      });

      if (response.ok) {
            alert('File uploaded successfully!');
            window.location.reload();
      }
});

const getAllFiles = async () => {
      const response = await fetch(`${baseUrl}/all`, {
            method: 'GET',
            headers: {
                  'Content-Type': 'application/json'
            }
      });
      const data = await response.json();
      return data;
}

const deleteFile = async (file) => {
      const response = await fetch(`${baseUrl}/delete/${file}`, {
            method: 'DELETE',
            headers: {
                  'Content-Type': 'application/json'
            }
      });

      if (response.ok) {
            alert('File deleted successfully!');
            window.location.reload();
      }
}

const download = (blob, fileName) => {
      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.download = fileName;
      link.click();
      window.URL.revokeObjectURL(link.href);
}

const downloadFile = async (file) => {
      const response = await fetch(`${baseUrl}/download/${file}`, {
            method: 'GET',
            headers: {
                  'Content-Type': 'application/json'
            }
      });

      const blob = await response.blob();
      download(blob, file);
}

const fillFiles = async () => {
      const files = await getAllFiles();
      const filesContainer = document.getElementById('files');

      files.forEach(file => {
            const fileContainer = document.createElement('div');
            fileContainer.classList.add('file');

            const fileName = document.createElement('p');
            fileName.textContent = file;

            const downloadLink = document.createElement('button');
            downloadLink.textContent = 'Download';
            downloadLink.classList.add('download-button');
            downloadLink.onclick = () => downloadFile(file);

            const deleteButton = document.createElement('button');
            deleteButton.classList.add('delete-button');
            deleteButton.onclick = () => deleteFile(file);
            deleteButton.textContent = 'Delete';

            fileContainer.append( fileName, downloadLink, deleteButton);
            filesContainer.append(fileContainer);
      })
}

fillFiles();