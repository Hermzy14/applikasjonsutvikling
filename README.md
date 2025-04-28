# Applikasjonsutvikling
This is the backend to our final exam in IDATA2301 Webteknologi and IDATA2306 Applikasjonsutvikling.

## Environment variables
To run this project, you need to set the following environment variables:
- `DB_URL`: The URL to the database.
- `DB_USERNAME`: The username for the database.
- `DB_PASSWORD`: The password for the database.
- `FILE_UPLOAD_DIR`: The path to the directory where files will be uploaded.

Later on will also need JWT secret and JWT expiration time, but this is not yet implemented:
- `JWT_SECRET`: The secret key for JWT authentication.
- `JWT_EXPIRATION`: The expiration time for JWT tokens (in seconds).

See `.env.example` for an example of how to set these variables. 
You can create a `.env` file in the root directory of the project and set the variables there.