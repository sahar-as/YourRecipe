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

<ul>
  <li>It has been implemented with MVVM design pattern</li>
  <li>Firstly, the application will run after showing a splash</li>
  <li>On the Main Page, it shows all Dishes both from the internet or imported manually</li>
  <li>There are “add” and “Filter” icons in the action bar
    <ul>
      <li>You can add a new recipe with your desired picture from the gallery or camera. (it uses Room library to save data)</li>
      <li>You can also filter represented images by their type</li>
    </ul>
  </li>
  <li>After selecting each dish from the Main page you will direct to the Detail Page, where you are able to read the whole text of the recipe and also copy or send page dish information</li>
  <li>There is a popup menu to Edit and Delete each dishes</li>
  <li>At the bottom of the application you can see a Bottom menu that directs you to the two pages:
  <ul>
      <li>Favorit</li>
      <li>Random Dish: with the help of retrofit this fragment will get a random dish from spoonacular.com API, after each loading and after each swipe down because it has been implemented with SwipeRefreshLayout</li>
    </ul>
  </li>
  <li>This application has a Worker Manager class and it will show a notification of a new online dish per day with clicking on it you will be redirected to the Random Dish fragment</li>
</ul>

