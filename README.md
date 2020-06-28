<p align="center">
<img width="200" height="100" src="https://4.bp.blogspot.com/-oDAbn7pY67o/WrwYZVEVj8I/AAAAAAAAMFY/hV9iHsA0K7UTMVZvLI-xe072mEkzQrpXACLcBGAs/s1600/logo%2Bsantander%2Bnuevo2.jpg">
</p>


# Midterm Project - Build a Banking System

<p align="center"><strong> Ana Martins Santos </strong></p>

* [Goal](#goal)

* [Methodology](#methodology)

* [Tools](#tools)

* [How it works](#how-it-works)

* [Documentation](#documentation)

* [Test Coverage](#test-coverage)

* [Diagram Classes](#diagram-classes)

## <a name="goal"></a>Goal
Build a Banking System Application which has **4** types of **Account** (**Savings**, **Credit Card**, **Checking**,**Student Checking**) and **3** types of **User** (**Admin**, **Account Holder**, **Third Party**). This project was built under the requirements from [Ironhack Student Portal](https://my.ironhack.com/lms/courses/course-v1:IRONHACK+JAVA_MASTER+2020_v1/units/38369b876cb3446ab27a8da4003a7702).

## <a name="methodology"></a>Methodology
**Admins** have total authority on the application, they are able to create Third Party and Account Holders, create savings accounts, create credit card accounts, create checking and student checking accounts depending on the age of the primary owner, check all accounts balance, debit and credit amounts on every account, and filter all accounts based on different attributes. Each type of account has different features and default values defined if values provided aren't within the standard. 

**Account Holders** can check balance from own account and make a transference to their own accounts or to others. **Third Parties** are created by Admins and they are able to debit and credit amounts in any account when their hashed key, account id and account secret key are provided correctly. An important detail is that every User must be logged in before executing any action.

Some aditional points are that savings accounts receive every year an increment in balance based on their interest rate and the balance they have at the moment and credit cards accounts receive every month an increment in balance based on their interest rate as well.

In terms of **fraud detection**, the day when any account, which has a status feature, is created the owners are able to make only 3 transfers, when trying to make the fourth one the account will be **frozen**. This happens because the established **maximum number of transfers to begin with is 2**, when the daily transfer pass a **150%** of this maximum number of transferences the account will be frozen. If the account goes back to active, the owner will again be able to make transferences until they reach the new maximum number established, which was updated with the number of transferences made before it got freeze.

On the other hand, any account (with status feature) trying to make **more than 2 transfers** in a **second period** of time will be **frozen** as well. 

Once an account is frozen the **admins** have the power to **unfreeze** it.

All this complex functionally can be seen from the **Use Case Diagram** displayed below.

<p align="center">
<img width="680" height="600" src="https://i.ibb.co/vPZTdb7/Banking.jpg">
</p>

The **connection of all tables** generated in **MySQL** can be seen bellow.
<p align="center">
<img width="680" height="750" src="https://i.ibb.co/jwXc7q2/tables.png">
</p>

## <a name="tools"></a>Tools
- IntelliJ (Compile and run Java Program, JDK 11)
- Spring Boot Dependencies (Spring Boot DevTools, Mysql Driver, Spring Data JPA, Spring Web)
- MySQL
- Postman
- Swagger (API Document with HTML)
- Drawio (Draw User Case Diagram and Class Diagram)

## <a name="how-it-works"></a>How it works
<p align="center"><strong> Disclaimer: Any doubts on what commands to write consult the Documentation section below. </strong></p>

1. Clone the **MidtermProject-BankingSystem** repository on your local computer.

2. Access the folder **"MidtermProject"** in your IntelliJ.

3. Go into your MySQL Workbench and type the following commands ```CREATE SCHEMA banking_system```, ```CREATE SCHEMA banking_system_test```.

<p align="center">
  <img width="700" height="450" src="https://i.ibb.co/HrT4yLz/Screenshot-from-2020-06-27-10-04-28.png">
</p>

4. Copy and paste the following code on you SQL sheet file to insert all values needed to security the app.
  
**create table user** (
	  id bigint **auto_increment not null primary key**,
    username **varchar(255)**,
    password **varchar(255)**
);
  
**create table role** (
	  id **bigint auto_increment not null primary key**,
    role **varchar(255)**,
    user_id **bigint**,
    **foreign key** (user_id) **references** user (id)
);

**insert into user** (username, password) **values**

("accountholder", "$2a$10$AgadORqev5mlOFdF9FEPNekn2QVbWMKB.LlTmdARb6SlWMEaMUR.W"),

("thirdparty", "$2a$10$ud8cH6yWd5Rv81g0I/pflOxqkJo8xOXKxdJcPigdo8J9vZSGd2qFi"),

("admin","$2a$10$3NEBwP8JyukhGbkgNCA/jueUFPpbQNwXxLr8kY9dKMPc6y89sPY5S");

**insert into role** (role, user_id) **values**

("ROLE_ACCOUNTHOLDER", 1),

("ROLE_THIRDPARTY", 2),

("ROLE_ADMIN", 3),

("ROLE_ACCOUNTHOLDER", 3),

("ROLE_THIRDPARTY", 3);

5. Type the command ```mvn spring-boot:run``` in the terminal of your IntelliJ.
                                  
 5. Open the **Postman** in your terminal and then you can start making requests. 
 The requests allows you to CRUD (**create, read, update and delete**) all entities you desire (Account, User) and allows you to filter Accounts and Users by attribute.
 
This [LINK](https://www.getpostman.com/collections/d90673f14fafa51e4dfa) will allow you to use the Postman already created and make any request desired.

 <p align="center">
<img width="300" height="750" src="https://i.ibb.co/6yPsFVv/Screenshot-from-2020-06-27-10-08-37.png">
</p>

## <a name="documentation"></a>Documentation
* **JavaDOC**: The documentation for all methods from the Java program is available in this GitHub Repository in the folder **documentation** and in the field **index.html** as seen bellow.

<p align="center">
<img width="550" height="750" src="https://i.ibb.co/Xzxpn2X/Screenshot-from-2020-06-27-21-51-00.png">
</p>

* Postman Documentation written: [![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/preview/11598200-7d6af6f6-0119-42ee-b3d6-f9ee2cb2ad03?versionTag=latest&apiName=CURRENT&version=latest&documentationLayout=classic-double-column&top-bar=ffffff&right-sidebar=303030&highlight=ef5b25)

* Postman Documentation to prove: [![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/22d7b83db24ca94c76d1)

* **Swagger**:The documentation for all requests is available in [Swagger](http://localhost:8080/swagger-ui.html) by entering the following link (http://localhost:8080/swagger-ui.html) while having the program rolling in the terminal.

1. When you enter [THIS LINK](http://localhost:8080/swagger-ui.html) you'll find the following configuration, where all models specifications are displayed and the routes of the requests with their own specifications as well.

<p align="center">
<img width="550" height="390" src="https://i.ibb.co/FD03HPg/Screenshot-from-2020-06-27-12-14-39.png">
</p>

2. For each controllers we can see what type of requests are available and in detail which parameters we need to pass to make it happen correctly, bellow there's an example for the Admin Controller.

<p align="center">
<img width="550" height="390" src="https://i.ibb.co/tMh9qsc/Screenshot-from-2020-06-27-12-14-52.png">
</p>

3. You can also check the details from all entities created.

<p align="center">
<img width="550" height="390" src="https://i.ibb.co/t2tr4fS/Screenshot-from-2020-06-27-12-15-01.png">
</p>

## <a name="test-coverage"></a>Test Coverage
* In the **Java Program** in IntelliJ our test coverage is **100% for classes**, **97% for methods** and **90% for lines**.

* In the **Postman** every request is tested (**48 requests -> 48 tests passed**).

Note: To execute tests in Postman you must follow the next few intructions displayed below:

1. Open your Command Line and type ```postman``` and it'll open the framework.

2. Select the option ```Runner``` as seen bellow.

<p align="center">
<img width="450" height="350" src="https://i.ibb.co/0qJ0WB6/Screenshot-from-2020-06-27-13-42-27.png">
</p>

3. It'll open a new window (check figure bellow), make sure you select the **collection** ```Banking System Tests```, in the **enviroment** option select ```Test Banking``` and then ```Run Banking System Tests```.

<p align="center">
<img width="580" height="390" src="https://i.ibb.co/dGfZRzz/Screenshot-from-2020-06-27-13-27-42.png">
</p>

4. Then it'll open this window (check figure bellow), and you'll see 88 tests in green meaning that all test passed, given the **100% of coverage** for requests.

<p align="center">
<img width="580" height="390" src="https://i.ibb.co/SftWqVM/Screenshot-from-2020-06-27-13-27-12.png">
</p>

## <a name="diagram-classes"></a>Diagram Class
The diagram class bellow contains the entity's and classes in which they depend on.

<p align="center">
<img width="580" height="1000" src="https://i.ibb.co/qdgNpzN/Banking-Diagram-Classes.jpg">
</p>

To see it more in detail click [HERE](https://drive.google.com/file/d/1cCUxhahqq1OD__tHFlvAAGDUVmcCZj5Y/view?usp=sharing)!



