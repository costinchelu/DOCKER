Oracle Database Server 12c Docker Image Documentation
=================================================

### Optional wsl configuration for Windows

To prevent Oracle Database Enterprise Edition docker container and WSL2 from consuming all of your 
computer’s memory and CPU before you attempt to run the Oracle Database Enterprise Edition docker image. 

Open Windows Terminal/CMD/PowerShell and run the commands below:

```plaintext
wsl --shutdown
notepad "$env:USERPROFILE/.wslconfig"
```

Edit .wslconfig file with notepad and save.
```plaintext
[wsl2]
memory=3GB   # Limits memory in WSL 2
processors=4 # Limits number of processors in WSL2
```

### Accepting the terms of service

From the store.docker.com website accept `Terms of Service` for Oracle
Database Enterprise Edition.

### Login to Docker Store

Login to Docker Store with your credentials

`$ docker login`

### Starting an Oracle Database Server instance

Run Docker Image and map ports to host system to connect from outside the docker container.

`docker run -d -it --name <Oracle-DB> --network="bridge" -p 1521:1521 -p 5500:5500  store/oracle/database-enterprise:12.2.0.1`

where `<Oracle-DB>` is the name of the container and `12.2.0.1` is the
Docker image tag. The database server is ready to use when the `STATUS` field shows
`(healthy)` in the output of `docker ps`.

### Reusing existing database

This Oracle database server image uses Docker data volumes to store data
files, redo logs, audit logs, alert logs and trace files. The data
volume is mounted inside the container at `/ORCL`. To start a database
with a data volume using `docker run` command,

`$ docker run -d -it --name <Oracle-DB> --network="bridge" -p 1521:1521 -p 5500:5500 -v v1oracledb:/ORCL store/oracle/database-enterprise:12.2.0.1`

`v1oracledb` is the data volume that is created by Docker and mounted
inside the container at `/ORCL`. The persisted data files can be reused
with another container by reusing the `v1oracledb` data volume.

### Using host system directory for data volume

To use a directory on the host system for the data volume,

`$ docker run -d -it --name <Oracle-DB> -v /data/OracleDBData:/ORCL store/oracle/database-enterprise:12.2.0.1`

where `/data/OracleDBData` is a directory in the host system.

### Connecting to the Database Server Container

The default password to connect to the database with `sys` user is
`Oradoc_db1`.

#### Connecting from within the container

The database server can be connected to by executing SQL\*Plus,

`$ docker exec -it <Oracle-DB> bash -c "source /home/oracle/.bashrc; sqlplus /nolog"`

#### Connecting from outside the container (sqlplus)

The database server exposes port 1521 for Oracle client connections over
SQL*Net protocol and port 5500 for Oracle XML DB. SQL*Plus or any JDBC
client can be used to connect to the database server from outside the
container.

* To connect from outside the container start the container with `-P` or
`-p` option as,

`$ docker run -d -it --name <Oracle-DB> -P store/oracle/database-enterprise:12.2.0.1`

option `-P` indicates the ports are allocated by Docker. The mapped port
can be discovered by executing

`$ docker port <Oracle-DB>` `1521/tcp -> 0.0.0.0:<mapped host port>`

Using this `<mapped host port>` and `<ip-address of host>` create
`tnsnames.ora` in the directory pointed to by environment variable
`TNS_ADMIN`.

    ORCLCDB=(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=<ip-address of host>)(PORT=<mapped host port>))
        (CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=ORCLCDB.localdomain)))
    ORCLPDB1=(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=<ip-address> of host)(PORT=<mapped host port>))
        (CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=ORCLPDB1.localdomain)))

* To connect from outside the container using SQL\*Plus,

`$ sqlplus sys/Oradoc_db1@ORCLCDB as sysdba`

#### Connecting from outside the container (SQL Developer)

Connect with **sys** user:
	
```Yaml
# default username and password for SYS user
Username: sys
Password: Oradoc_db1

Hostname: localhost
Role: SYSDBA
Port: 1521

[FOR CDB]
SID: ORCLCDB

[FOR PDB]
Service_name: ORCLPDB1.localdomain
```

Creating users:

```sql
-- verify connection
SHOW con_name; -- CDB$ROOT

-- verify existent users
SELECT username, common, con_id FROM cdb_users;
SELECT DISTINCT username FROM cdb_users WHERE common='YES';
SELECT username, con_id FROM cdb_users WHERE common='NO';

-- verify created PDBs
SELECT pdb_name FROM cdb_pdbs; --ORCLPDB1 (oracle 12 container comes with a pdb)

-- create a common (CDB) user (available in all of the containers):
CREATE USER c##_test_user IDENTIFIED by pass123 CONTAINER=ALL;

-- grant all rights to the common user
GRANT ALL PRIVILEGES TO c##_test_user CONTAINER=ALL;

-- similarly we can create a local user at PDB level. For that we need to first connect to the PDB and then create a new user and grant privileges.
```

#### Connecting from outside the container (Datagrip)

After creating a common or PDB user:

```Yaml
Host: localhost
Port: 1521
User: c##_test_user
Password: pass123

[for PDB1]
Service: ORCLPDB1.localdomain

[for CDB]
SID: ORCLCDB

URL: jdbc:oracle:thin:@//localhost:1521/ORCLPDB1.localdomain
```

### Custom Configurations

The Oracle database server container also provides custom configuration
parameters for starting up the container. All the custom configuration
parameters are optional. The following list of custom configuration
parameters can be provided in the ENV file (`ora.conf`).

#### `DB_SID`

This parameter changes the `ORACLE_SID` of the database. The default
value is set to `ORCLCDB`.

#### `DB_PDB`

This parameter modifies the name of the PDB. The default value is set to
`ORCLPDB1`.

#### `DB_MEMORY`

This parameter sets the memory requirement for the Oracle server. This
value determines the amount of memory to be allocated for SGA and PGA.
The default value is set to 2GB.

#### `DB_DOMAIN`

This parameter sets the domain to be used for database server. The
default value is `localdomain`.

To start an Oracle database server with custom configuration parameters

`$ docker run -d -it --name <Oracle-DB> -P --env-file ora.conf store/oracle/database-enterprise:12.2.0.1`

Ensure custom values for `DB_SID`, `DB_PDB` and `DB_DOMAIN` are updated
in the tnsnames.ora.

Caveats
-------

This Docker image has the following restrictions.

1.  Supports a single instance database.
2.  Dataguard is not supported.
3.  Database options and patching are not supported.

Changing default password for SYS user
--------------------------------------

The Oracle database server is started with a default password
`Oradoc_db1`. The password used during the container creation is not
secure and should be changed. To change the password connect to the
database with SQL\*Plus and execute

`ALTER USER sys IDENTIFIED BY <new-password>;`

Resource Requirements
---------------------

The minimum requirements for the container is 8GB of disk space and 2GB
of memory.

Database Logs
-------------

The database alert log can be viewed with

`$ docker logs <Oracle-DB>`

Oracle Database Server 12.2.0.1 Enterprise Edition Slim Variant
---------------------------------------------------------------

The Slim Variant (`12.2.0.1-slim` tag) of EE has reduced disk space
(4GB) requirements and a quicker container startup. This image does not
support the following features - Analytics, Oracle R, Oracle Label
Security, Oracle Text, Oracle Application Express and Oracle DataVault.
To use the slim variant

`$ docker run -d -it --name <Oracle-DB> store/oracle/database-enterprise:12.2.0.1-slim`

where `<Oracle-DB>` is the name of the container and `12.2.0.1-slim` is
the Docker image tag.