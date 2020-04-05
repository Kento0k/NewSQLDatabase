### Build and run:
To build this application you should use gradle build.
To use this application, you should put a file with your database structure to project resources (src/main/resources) and name it "structure.yaml".
Then build a JAR artifact "generator.jar"
It`s a command line app with 3 command line arguments on input: table pattern, query, case sensitive
### Examples
- Input: java -jar generator.jar .*/.actor true true
- Output:
Schema: sakila
	Table: actor
	Columns:
		Name: actor_id
		Type: integer
		Name: first_name
		Type: varchar(45)
		Name: last_name
		Type: varchar(45)
		Name: last_update
		Type: date
		Name: has_kids
		Type: boolean
	Table: address
	Columns:
		Name: address_id
		Type: integer
		Name: city_id
		Type: integer
SELECT * FROM sakila.actor
WHERE first_name LIKE '%true%'
OR last_name LIKE '%true%'
OR has_kids = true

