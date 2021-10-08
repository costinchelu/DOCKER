FROM node:16-alpine

# set environment variables
ENV MONGO_DB_USERNAME=admin \
    MONGO_DB_PWD=password

# directory is created inside the container
RUN mkdir -p /home/app

# copy app content from the host to the created folder inside the image
COPY ./app /home/app

# set default dir so that next commands executes in /home/app dir
WORKDIR /home/app

# will execute npm install in /home/app because of WORKDIR
RUN npm install

# no need for /home/app/server.js because of WORKDIR
CMD ["node", "server.js"]

# run from the console with: docker build -t my-app:1.0 .