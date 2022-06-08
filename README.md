# Task Master

## What we do today?

We create three activity: one for the main page to show image with two buttuon one of them for add task and the other one to show all of the tasks. Also, we create an add task activity which allow to us to enter our tasks and the last activity to show all the tasks that user added and We will add the functionality of them later. 

## screenshot

- The main Activity  
![image description](screenshots/image1.jpeg)
- Activity to add tasks
![image 2](https://i.ibb.co/mtNJXZ5/photo1650898777-1.jpg)
- Activity to show all of the tasks
![image three](https://i.ibb.co/wsGBywy/photo1650898777.jpg)


## Lab - 27

---
- The following function is handled:

1. Setting Page: to add a username and save it using sharedPreferences.
![Setting Page](screenshots/usernameSaved.jpeg)

2. Details Page: to show data (hard coded until now) from the home page
![android task](screenshots/androidTask.jpeg)
![coffee task](screenshots/CoffeeTask.jpeg)
![design task](screenshots/DesignTask.jpeg)

3. add a navigation bar in the top bar.
4. show the user name inside the home page
![navbar](screenshots/navbar.jpeg)

## Lab - 29

----

The database saved the new task with the state of this task and the description.

![edit main Page](screenshots/Lab-29/mainPage.jpeg)
![Add task Page](screenshots/Lab-29/addtask.jpeg)
![All of the Tasks](screenshots/Lab-29/tasks.jpeg)
![details of the Task](screenshots/Lab-29/details.jpeg)

## Lab - 31

---
Espresso Testing Unit was added 

Editing UserName is showed below:

![video of Testing](screenshots/Lab31/SettingTest.gif)

![Result of Testing On IntelliJ](screenshots/Lab31/ResultOftestSetting.png)

Check the Important UI Element and the navigation from the HomePage to Task Details

![navigateTest](screenshots/Lab31/navigationTest.gif)

![GIT UI](screenshots/Lab31/UiTest.gif)

![Result of UI test](screenshots/Lab31/resultOfTestingUI.png)

## Lab - 32

---

1. Amplify added to application
2. Amplify client created in Application class
3. Data is posted and displayed based on DynamoDB status, not local DB status

**HomePage:**

![home](screenshots/Lab32/HomePage.jpeg)

**All Tasks:**

![allTasks](screenshots/Lab32/allTasks.jpeg)

**TaskDetails:**

![TaskDetails](screenshots/Lab32/taskDetails.jpeg)

## Lab - 33

---

1. Team entity added with a reasonable structure
2. Add a team then associate teams with tasks
3. pt Show only the selected team’s tasks on homepage

**Home Page When We Start Application :**

![home](screenshots/Lab33/HomePage.jpeg)

**Select TEAM:**

![select team](screenshots/Lab33/SettingPage.jpeg)

**After Selected TEAM:**

![TaskDetails](screenshots/Lab33/homePageAfterSelectTeam.jpeg)

**Add Team Selected To AddTask Page:**

![TaskDetails](screenshots/Lab33/addTask.jpeg)

## Lab - 34

---
We created Final AAB and add it to the root.

> To share our Application on Google we need to:

1. open Google console then create a new app
2. choose the name and these stuff then accept the developer policies and US export laws, then click on create App

3. go to set up your app and notify them about your application's content like: app access, Ads, content rating, target audience, and so on
4. then set up your store listing
5. after that create new release and upload the abb or apk
6. add some release notes then click on review release
7. click on start rollout to Production
8. the application is sent to review after the approval the app will be live on google play store

## Lab - 36

---

1. AddingCognito added with sign up page
![sign up](screenshots/Lab36/signup.jpeg)

2. Adding sign up verification page
![verfy](screenshots/Lab36/verification.jpeg)

3. Create login page
![login](screenshots/Lab36/login.jpeg)

4. Add Logout functionality
![logout](screenshots/Lab36/logout.jpeg)
![logout click](screenshots/Lab36/logout-button.jpeg)

5. AWS
![AWS](screenshots/Lab36/aws.png)

## Lab - 37

---
Uploads
On the “Add a Task” activity, allow users to optionally select an image to attach to that task. If a user attaches an image to a task, that image should be uploaded to S3, and associated with that task.
![select image](screenshots/Lab37/select-image.jpeg)

**AWS Side**

![AWS side](screenshots/Lab37/aws-image.png)

Displaying Files
On the Task detail activity, if there is a file that is an image associated with a particular Task, that image should be displayed within that activity.
![display](screenshots/Lab37/display-image.jpeg)

## Lab - 38

---

1. Add an intent filter to your application such that a user can hit the “share” button on an image in another application, choose TaskMaster as the app to share that image with,

![share](screenshots/Lab38/share_image.jpeg)

2. Take the image directly to the Add a Task activity with that image pre-selected

  ![pre-select image](screenshots/Lab38/add_task_page.jpeg)

## Lab - 39

---

Adding permission to the project get the coordinates to the user when we add a new task save the coordinate and push them to AWS DynamoDB

## Lab - 41

---

1. Add analytics event to your app
open aws dashboard using:

> amplify console analytics

![aws dashboard](screenshots/Lab41/aws-analytics.png)

2. Add a button to read and translate the task description

![](screenshots/Lab41/translate-the-description.jpeg)