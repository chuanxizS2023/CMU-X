# Build react application
FROM node AS build

# Set working directory
WORKDIR /app-docker

# Copy package.json
COPY package.json ./

# install dependencies
RUN npm install

# Copy all files
COPY . ./

# Build react application
RUN npm run build

# Serve react application from nginx
FROM nginx:alpine

# Copy build files to nginx
COPY --from=build /app-docker/build /usr/share/nginx/html

# Expose port 80(default port for nginx, if wish to change,
# write in nginx.conf file and copy it to /etc/gnginx/conf.d/)
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]