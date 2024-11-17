## Runner
``` mvn spring-boot:run -Dspring-boot.run.arguments="$1 $2"  ```
## Example
```  mvn spring-boot:run -Dspring-boot.run.arguments="iJhz 5432"  ```
```
[ {
  "id" : "iJhz",
  "destination_id" : 5432,
  "name" : "Beach Villas Singapore",
  "location" : {
    "lat" : 1.264751,
    "lng" : 103.824006,
    "address" : "8 Sentosa Gateway, Beach Villas, 098269",
    "city" : "Singapore",
    "country" : "Singapore"
  },
  "description" : "Located at the western tip of Resorts World Sentosa, guests at the Beach Villas are guaranteed privacy while they enjoy spectacular views of glittering waters. G
uests will find themselves in paradise with this series of exquisite tropical sanctuaries, making it the perfect setting for an idyllic retreat. Within each villa, guests will disc
over living areas and bedrooms that open out to mini gardens, private timber sundecks and verandahs elegantly framing either lush greenery or an expanse of sea. Guests are assured 
of a superior slumber with goose feather pillows and luxe mattresses paired with 400 thread count Egyptian cotton bed linen, tastefully paired with a full complement of luxurious i
n-room amenities and bathrooms boasting rain showers and free-standing tubs coupled with an exclusive array of ESPA amenities and toiletries. Guests also get to enjoy complimentary day access to the facilities at Asia?s flagship spa ? the world-renowned ESPA.",
  "amenities" : {
    "general" : [ "indoor pool", "outdoor pool", "business center", "dry cleaning", "childcare", "pool", "wi fi", "breakfast" ],
    "room" : [ "tub", "tv", "iron", "aircon", "hair dryer", "coffee machine", "kettle" ]
  },
  "images" : {
    "rooms" : [ {
      "link" : "https://d2ey9sqrvkqdfs.cloudfront.net/0qZF/3.jpg",
      "description" : "Double room"
    }, {
      "link" : "https://d2ey9sqrvkqdfs.cloudfront.net/0qZF/4.jpg",
      "description" : "Bathroom"
    }, {
      "link" : "https://d2ey9sqrvkqdfs.cloudfront.net/0qZF/2.jpg",
      "description" : "Double room"
    } ],
    "site" : [ {
      "link" : "https://d2ey9sqrvkqdfs.cloudfront.net/0qZF/1.jpg",
      "description" : "Front"
    } ],
    "amenities" : [ {
      "link" : "https://d2ey9sqrvkqdfs.cloudfront.net/0qZF/6.jpg",
      "description" : "Sentosa Gateway"
    }, {
      "link" : "https://d2ey9sqrvkqdfs.cloudfront.net/0qZF/0.jpg",
      "description" : "RWS"
    } ]
  },
  "booking_conditions" : [ "Guests are required to show a photo identification and credit card upon check-in. Please note that all Special Requests are subject to availability and 
additional charges may apply. Payment before arrival via bank transfer is required. The property will contact you after you book to provide instructions. Please note that the full 
amount of the reservation is due before arrival. Resorts World Sentosa will send a confirmation with detailed payment information. After full payment is taken, the property's detai
ls, including the address and where to collect keys, will be emailed to you. Bag checks will be conducted prior to entry to Adventure Cove Waterpark. === Upon check-in, guests will
 be provided with complimentary Sentosa Pass (monorail) to enjoy unlimited transportation between Sentosa Island and Harbour Front (VivoCity). === Prepayment for non refundable boo
kings will be charged by RWS Call Centre. === All guests can enjoy complimentary parking during their stay, limited to one exit from the hotel per day. === Room reservation charges
 will be charged upon check-in. Credit card provided upon reservation is for guarantee purpose. === For reservations made with inclusive breakfast, please note that breakfast is ap
plicable only for number of adults paid in the room rate. Any children or additional adults are charged separately for breakfast and are to paid directly to the hotel.", "Free priv
ate parking is possible on site (reservation is not needed).", "Pets are not allowed.", "All children are welcome. One child under 12 years stays free of charge when using existing
 beds. One child under 2 years stays free of charge in a child's cot/crib. One child under 4 years stays free of charge when using existing beds. One older child or adult is charge
d SGD 82.39 per person per night in an extra bed. The maximum number of children's cots/cribs in a room is 1. There is no capacity for extra beds in the room.", "WiFi is available in all areas and is free of charge." ]
} ]

```
## Approach description
1. Class Structure and Data
- Supplier :
  + An abstract ```BaseSupplier``` class and subclasses like ```AcmeSupplier```,```PatagoniaSupplier```,```PaperfliesSupplier```
  + These suppliers are required to implement ```endpoint()``` (the api url of the supplier) and ```parse()``` (converting data from the api into ```Hotel``` object)
  + These suppliers are automatically registered through ```SupplierFactory```, allowing for easy addition or management of suppliers
- Hotel :
  + Containing detailed information about the hotel, including ```name```,```location```,```amenities```,```images```. These attributes can be merged between hotels if they have the same ```id```.
  + The ```Hotel``` class also implements the ```Mergeable``` interface to support merging information from other ```Hotel``` objects. Subclasses like ```Amenities```,```Location```,```Images``` also support similar merging behavior.
2. Data merging process
- Fetching data from suppliers :
  + The suppliers fetch data from external APIs. The data is then parsed and converted into ```Hotel``` objects through ```parse()``` method
- Merging Hotel Data :
  + After collecting data from all the suppliers, using ```mergeAndSave()``` method in ```HotelService``` class to merge hotels with the same ```id```.
  + This method uses a ```Map<String,Hotel>``` to store hotels by their ```id```. If the new hotel has an ```id``` that already exists in the map, it calls the ```merge()``` method of the ```Hotel``` object to combine data. The merging process ensures that all the non-null attributes of the new hotel will be updated in the existing hotel.
  + If the hotel does not exist in the map, it is added to the map.
- Filtering data :
  + When a request to search for the hotels , the ```HotelService``` class provides the ```find()``` method to filter hotels by ```hotelIds``` and ```destinationIds```. The filtered hotels are returned as a list, allowing to search based on criteria.
 3. Benefits of the solution
- Extensibility :
  + Using ```Supplier``` classes and ```SupplierFactory``` allows the system to be easily extended. We can add new suppliers without changing the core of structure of the application -simple create a new ```Supplier``` class and register it through ```SupplierFactory```
- Data merging capability :
  + The data merging process ensures that hotels from different suppliers are combined accurately and completely. If there are discrepancies between different versions of the same hotel, the new data will be fill in missing fields or update existing fields with new values.
- Easy to manage and maintain :
  + By separating the responsibilities of each class (e.g., the ```BaseSupplier``` class only fetches data, while the ```HotelService``` class handles merging and searching), the code is easier to maintain and extend.

 

