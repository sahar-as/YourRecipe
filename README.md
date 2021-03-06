# YourRecipe
<p align="center">
  <img src="https://user-images.githubusercontent.com/63088252/171119093-bd3b178a-14c8-4228-aa43-69b455b49f19.jpg" />
</p>

Your Recipe is an Android application for saving favorite recipes, online and local, using the Kotlin programming language.<br />
It makes use of the following libraries: <br />
- Glide 
- Room
- Retrofit
- RxJava
- Worker Manager
- Navigation
- Androidx
 <br />

### 📕 Application Features:

<ul>
  <li>It has been implemented with <b>MVVM</b> design pattern</li>
  <li>Firstly, the application will run after showing a splash</li>
  <li>On the Main Page, it shows all Dishes both from the internet or saved manually</li>
  <li>There are <b>add</b> and <b>Filter</b> icons in the action bar
    <ul>
      <li>You can <b>Add</b> a new recipe with your desired picture from the <b>gallery</b> or <b>camera</b></li>
      <li>You can also <b>Filter</b> represented dishes by their type</li>
    </ul>
  </li>
  <li>After selecting each dish from the Main page you will direct to the <b>Detail Page</b>, where you are able to read the whole text of the recipe and also <b>Copy</b> or <b>Share</b> its information</li>
  <li>There is a <b>popup menu</b> to Edit and Delete each dishes</li>
  <li>At the bottom of the application you can see a <b>Bottom menu</b> that directs you to the two pages:
  <ul>
      <li><b>Favorit</b></li>
      <li><b>Random Dish:</b> with the help of retrofit this fragment will get a random dish from <b>spoonacular.com</b> API, after each loading and after each swipe down because it has been implemented with <b>SwipeRefreshLayout</b></li>
    </ul>
  </li>
  <li>This application has a <b>Worker Manager</b> class and it will show a <b>notification</b> of a new online dish per day and with clicking on it you will be redirected to the Random Dish fragment</li>
</ul>
<br />
    
### 📺 App Screenshot
<br />
- <b>Splash Screen and Main Activity:</b> <br /><br /> 
<p align="center"><img src="https://user-images.githubusercontent.com/63088252/171140277-ed0f7236-ca60-4fb4-a4dc-ef407e519a79.png" /> <img src="https://user-images.githubusercontent.com/63088252/171132452-57b6fcd3-c38c-4401-834b-d66775b22f45.png" /></p>
<br />
- <b>Add Dish:</b> <br /><br /> 
<p align="center"><img src="https://user-images.githubusercontent.com/63088252/171132488-73ebcc49-867c-4c44-9cd1-83b9e527569a.png" />
<img src="https://user-images.githubusercontent.com/63088252/171138686-a1debd56-de7c-4fbc-96e4-d05e071eb6ec.png" /></p>
<br />
- <b>Filter Dish:</b> <br /><br /> 
<p align="center"><img src="https://user-images.githubusercontent.com/63088252/171138853-07fd31b7-5deb-45ec-9f4f-059938d76e11.png" />
<img src="https://user-images.githubusercontent.com/63088252/171138886-26599966-3601-4da3-ad87-56d0a5060991.png" /></p>
<br />
- <b>Detail Fragment and Share Information:</b> <br /><br /> 
<p align="center"><img src="https://user-images.githubusercontent.com/63088252/171152885-d51b60bf-6d86-483e-81e8-2a10a43f3ab5.png" />
<img src="https://user-images.githubusercontent.com/63088252/171139089-23bfefa8-9f3f-4cc5-8fff-bf1f338b33bd.png" /></p>
<br />
- <b>Edit & Delete:</b> <br /><br /> 
<p align="center"><img src="https://user-images.githubusercontent.com/63088252/171139257-48f5b756-98a2-4c62-b2bb-c9d1bf1ce1a0.png" />
<img src="https://user-images.githubusercontent.com/63088252/171139278-fafb042f-4800-477f-98e4-070257d49225.png" /></p>
<br />
- <b>Random Fragment and Swipe Layout:</b> <br /><br /> 
<p align="center"><img src="https://user-images.githubusercontent.com/63088252/171139687-501d5751-f716-44ab-b348-fb8a57c7823f.png" /></p>
<br />
- <b>Worker Manager & Notification:</b> <br /> <br /> 
<p align="center"><img src="https://user-images.githubusercontent.com/63088252/171139879-7d821591-e614-40b2-a90a-7827cc27364f.png" />
<img src="https://user-images.githubusercontent.com/63088252/171139896-22c184c2-69d8-49a3-a62c-00a055b9cf17.png" /></p>


