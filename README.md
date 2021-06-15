# GalleryAppJavaFX
Gallery application in JavaFX

Application has the following features:
Features
Your application must implement the following features:

1. Date of photo

2. Tags

Photos can be tagged with pretty much any attribute you think is useful to search on, or group by. Examples are location where photo was taken, and names of people in a photo, so you can search for photos by location and/or names.

Additional details:

You can set up some tag types beforehand for the user to pick from (e.g. location)
Depending on the tag type, a user can either have a single value for it, or multiple values (e.g. for any photo, location can only have one value, but if there's a person tag, that can have multiple values, one per person that appears in the photo)
A user can define their own tag type and add it to the list (so from that point on, that tag type will show up in the preset list of types the user can choose from)

3. Location of Photos - Stock photos and User photos

There are two sets of photos, stock photos that come pre-loaded with the application, and user photos that are loaded/imported by a user when they run the application.

* Stock photos are photos that you will keep in the application's workspace. You must have no fewer than 5 stock photos, and no more than 10.
Create a special username called "stock" (no password, or password="stock") and store the stock photos under this user, in an album named "stock".

* User photos are photos that your application can allow a user to load from their computer, so they can be housed anywhere on the user's machine. The actual photos must NOT be in your application's workspace. Instead, your application should only store the location of the photo on the user's machine. User photo information must NOT be in the released project in Bitbucket since each installation of your application on a machine will have its own set of users.

4. Login

When the application starts, a user logs in with username. Password implementation is optional. It makes for a "real" scenario, but is irrelevant to the essence of the project. (There is no credit for the password feature, if you choose to implement it.)

5. Admin Subsystem
The admin user can then do any of the following:
* List users
* Create a new user
* Delete an existing user

6. Non-admin User Subsystem

Once the user logs in successfully, all albums and photo information for this user from a previous session (if any) are loaded from disk.

The user can then do the following:
* Create albums
* Delete albums
* Rename albums
* Open an album. Opening an album displays all photos, with their thumbnail images and captions, inside that album. Once an album is open the user can do the following:
  * Add a photo
  * Remove a photo
  * Caption/recaption a photo
  * Display a photo in a separate display area. The photo display should also show its caption, its date-time of capture (see Date of photo below), and all its tags (see Tags below).
  * Add a tag to a photo
  * Delete a tag from a photo
  * Copy a photo from one album to another (multiple albums may have copies of the same photo)
Note: If a photo is in multiple albums, it is the same physical photo, just refereneced/contained in multiple albums. This means any changes you make to the photo (caption, tags) will be reflected in all the albums in which the photo appears.

  * Move a photo from one album (source) to another (the photo will be removed from the source album)
  * Go through photos in an album in sequence forward or backward, one at a time, with user interaction (manual slideshow)
* Search for photos (Photos that match the search criteria should be displayed in a similar way to how photos in an album are displayed):
  * Search for photos by a date range.
  * Search for photos by tag type-value pairs. The following types of tag-based searches should be implemented:
    A single tag-value pair, e.g person=sesh
    Conjunctive combination of two tag-value pairs, e.g. person=sesh AND location=prague
    Disjunctive combination of two tag-value pairs, e.g. person=sesh OR location=prague

7. Logout

The user (whether admin or non-admin) logs out at the end of the session. All updates made by the user are saved to disk.
After a user logs out, the application is still running, allowing another user to log in.

8. Quit Application

In the application all errors and exceptions should be handled gracefully within the GUI setup.
