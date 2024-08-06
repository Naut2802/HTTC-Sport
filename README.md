# HTTC-Sport

# Build Docker Image
  # Create network
    # docker network create network_name
  # Run mysql in network
    # docker run --network network_name --name name -p port:port -e MYSQL_ROOT_PASSWORD=password -d mysql:version
  # Build Docker Image with DockerFile
    # docker build -t name:version .
  # Run docker image
    # docker run --network network_name --name name --env-file .env -p port:port docker_image
