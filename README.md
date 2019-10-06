Product Catalog Secured Spring Boot REST API with JSON Web Token
-----------------------------------------------------------------

About
=====
A REST API for Product Catalog. There are several assumptions have been taken

    - A Product might belong to one or many Categories
    - A Category has one or many Products
    - CREATE, UPDATE, DELETE can be performed based on user roles
    
    - End Points for Categories
      To get all Categories 	- http://<localhost>:<8080>/categories
      To add a Category     	- http://<localhost>:<8080>/categories and the Category JSON in the body
      To Update a Category  	- http://<localhost>:<8080>/categories/{categoryId} and the category JSON in the body

    - End Points for Products
      To get all Products   	- http://<localhost>:<8080>/categories/{categoryId}/products
      To add a Product      	- http://<localhost>:<8080>/categories/{categoryId}/products and the Product JSON in the body,
 									in here only the Category which has {categoryId} will be mapped to the product
      To add a Product      	- http://<localhost>:<8080>/products and the Product JSON in the body including Category Ids,
        							in here all the Categories passed as a JSON array will be mapped to the product
      To Update a Product   	- http://<localhost>:<8080>/products and JSON in the body
      To Delete a Product   	- http://<localhost>:<8080>/products/{productId}
 
 
 
How To Run
==========

1. Clone the git repository
2. Perform,

        mvn clean install
        
3. And run the Project using,

        mvn spring-boot:run
       
   More details can be found [here](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html)
   
4. Defult port for the REST API is 8080 and will be served in the following URL (if you run locally)

        http://localhost:<8080>

5. There are alredy two types of users in the in memory database. Admin and a User. A bearer token will be required to perform CRUD operations. According to the give roles only Admin can perform CREATE, UPDATE, and DELETE operations while User can perform only CREATE operations. Anyone can perform GET operations without any tokens.

6. To get a Access token the command format is,

		curl client:secret@localhost:8080/oauth/token -d grant_type=password -d username=user -d password=pwd
		
	Based on the given properties in **application.properties** file and the already inserted data run the following commands,
	
	to generate access token for User,
	
		curl testjwtclientid:XY7kmzoNzl100@localhost:8080/oauth/token -d grant_type=password -d username=user -d password=jwtpass
		
	to generate access token for Admin,
	
		curl testjwtclientid:XY7kmzoNzl100@localhost:8080/oauth/token -d grant_type=password -d username=admin.admin -d password=jwtpass