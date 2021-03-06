# nk-ergonomics
This is a tool to be used at offices that will help the employers by reducing the harm caused by their stationary work for short activity breaks. It is designed to be non-interuptive and to improve the individual's healt as well as the over all working environment.

The tool consists of a chrome extension that communicates with a server that holds user info and scores.

## Starting the server:      
The server requires SQLite to run and Maven to build. SQLite is pre installed on Mac. Other systems may need to download and install separately.  
If not done so already, install maven 3. On a Mac, this can be done using homebrew:
```
brew install maven
```
On windows:  [installing maven](https://maven.apache.org/guides/getting-started/windows-prerequisites.html), [installing SQLite](http://johnatten.com/2014/12/07/installing-and-using-sqlite-on-windows/)
  
Build the project:
```
mvn package
```
The server can then be started using the command:
```
java -cp target/nk-ergonomics-1.0.jar Server
```


## Operations  
The server supports the following operations:  

| method | action             | parameters                                                                                                                                                                                                        | description                                                                                                                                                                                                                                     |
|--------|--------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| get    | /api/getUsers      | <ul><li>**office:** "SKELLEFTEÅ", "STOCKHOLM", "LA" </li>   <li>**name:** "FirstName LastName"</li></ul>                                                                                                          | Gets all the users that match any of the specified parameters. Meaning that additional parameters will likely increase number of returned users                                                                                                 |
| post   | /api/createUser    | user-object                                                                                                                                                                                                       | Creates a new user using the specified data. Example of a valid user can be found below                                                                                                                                                         |
| get    | /api/getHighScores | <ul><li>**startTime:** "timestamp" _(required)_</li><li>**endTime:** "timestamp"</li><li>**limit:** "number"</li><li>**office:** "SKELLEFTEÅ", "STOCKHOLM", "LA"</li><li>**name:** "FirstName LastName"</li></ul> | Returns all the HighScores that matches all the given parameters. Mening that additional parameters will likely reduce the size of the result set. Omitting endTime implies "now" as endTime. Omitting office will get scores from all offices. |
| post   | /api/postScores    | list of score-objects                                                                                                                                                                                             | Adds high scores to the server. Example of valid data can be found below                                                                                                                                                                        |


User example:
```json
{
  "firstName": "Linus",
  "lastName":"Lagerhjelm",
  "office":"STOCKHOLM"
}
```

High Score example:
```json
[
    {
    	"value": 10, 
	"timestamp": 1490208166633, 
	"user": {
		"id": 9,
		"firstName": "Linus",
		"lastName": "Lagerhjelm",
		"office": "STOCKHOLM"
	}
    },
    {
	"value": 11, 
	"timestamp": 1490208167633, 
	"user": {
		"id": 9,
		"firstName": "Linus",
		"lastName": "Lagerhjelm",
		"office": "STOCKHOLM"
	}
    }
]
```
