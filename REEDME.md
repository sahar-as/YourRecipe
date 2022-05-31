<p align="center">
  <img src="https://user-images.githubusercontent.com/63088252/171119093-bd3b178a-14c8-4228-aa43-69b455b49f19.jpg" />
</p>

Your Recipe is an Android application for saving favorite recipes, online and local, using the Kotlin programming language.
It makes use of the following libraries: <br />
- Glide 
- Room
- Retrofit
- RxJava
- Worker Manager
- Navigation
- Androidx
 <br />
 <br />

### 📕 Application Features:
- It has been implemented with MVVM design pattern.
-	Firstly, the application will run after showing a splash
-	On the Main Page, it shows all Dishes both from the internet or imported manually
-	There are “add” and “Filter” icons in the action bar
  -	You can add a new recipe with your desired picture from the gallery or camera. (it uses Room library to save data)
  *	You can also filter represented images by their type
•	After selecting each dish from the Main page you will direct to the Detail Page, where you are able to read the whole text of the recipe and also copy or send page dish information
•	There is a popup menu to Edit and Delete each dishes
•	At the bottom of the application you can see a Bottom menu that directs you to the two pages:
o	Favorit
o	Random Dish: with the help of retrofit this fragment will get a random dish from spoonacular.com API, after each loading and after each swipe down because it has been implemented with SwipeRefreshLayout
•	This application has a Worker Manager class and it will show a notification of a new online dish per day with clicking on it you will be redirected to the Random Dish fragment

