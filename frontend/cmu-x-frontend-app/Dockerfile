# Base image
FROM node:latest

# Set working directory
WORKDIR /app

# Install dependencies
# Only copy the package.json file to work directory
COPY package.json /app/
RUN npm install
COPY . /app/
# Expose port 3000
EXPOSE 3000

# Start the application
CMD ["npm", "start"]
