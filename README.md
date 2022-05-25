# A personal blog project for practice
## frontend:
1. login page (completed)
2. register page (completed)
3. home page (completed)
4. text editor page (completed)
5. blog reader page (completed)

---

## backend:
- [x] how to put new blog into database?
- [x] how toshow each blog as blog cards?
- [x] how to show specific blog when press read button from home page?
- [x] how to show username at the top left corner of each page?
- [x] how to delete a blog?
- [x] how to show the right author of the blog?
- [x] how to put the content of a blog into the editor?
- [x] how to add word amount restriction to textarea in html?
- [x] how to authenticate if the user can edit or delete the blog?
- [ ] how to store images that have been uploaded?
- [ ] how to put images into banners?


---

## day1(5.16):
1. github
2. login page (completed)
3. register page (completed)
4. home page (developing)
5. text editor page (developing)

## day2(5.17):
1. home page (completed)
2. text editor page (completed)
3. blog reader page (completed)

## day3(5.18):
1. put all the html&css files into maven framework
2. created a table for blogs which includes a foreign key(name) of 'user-info' table
3. added validation to registration form (the pattern of username and password & the repeatition of password)
4. added HomeController, EditorController, ReaderController
5. connected all the pages by getMapping 
6. put new blog into database

## day4(5.19):
1. activity diagram 
2. class diagram
3. openapi (10%)

## day5(5.20):
1. openapi (100%)

## day6(5.23)
1. printed username to navigation bar at home page
2. printed newly added blog to reader page
3. transfered username to all the pages using username=${username} to fake a logging-in status
   (will try to follow the spring security tutorial to make it legit if I have spare time after implementing all the basic functions)
   link: https://spring.io/guides/gs/securing-web/ 
4. showed all the blogs as blog cards at the home page
5. showed line breaks in thymeleaf
6. added multiline ellipsis in blog preview card (webkit only)
7. showed specific blog when press read button from home page
8. implemented the function of deleting a blog
9. showed the right author of the blog

## day7(5.24)
1. put the content of a blog into the editor when press edit button from reader page
2. implemented the function of updating a blog
3. implemented spring security（logging out unfinished)
4. added word count limit to the textarea of titles

## day8(5.25)
1. cleaned the codes that are meaningless after implementing Spring security
2. add author to new blog by new method ((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
3. made a button for logging out in top navbar
4. implemented the authorization checking if the user can edit or delete the blog
5. made top navbar sticky
6. blog-card hovering animation
7. showed placeholder image at the banner of reader and editor page
8. register page: return a div if password != repeat password


add date to each blog