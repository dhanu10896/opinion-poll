DONE
1. rest end points for user registration, email verification, login
2. rest end points for poll creation, voting, result counting
4. all the functional repositories and entities
3. GraphQL endpoints for poll related operations
4. migration to h2 database

TODO:
1. exception handling for all the rest endpoints
2. ui
 


HLD:
1. Requirements
	1. system will support authentication, authorization, different user roles     (google/facebook sign in, email verified username password authentication)  
	2. any user can will be able to create poll, with expiration Date Time  
	3. poll can have multiple questions with multiple choices  
	4. link for for poll will be shared with users once poll becomes active  
	5. other users will be add poll to watchlist if poll is public or else only admin will have access to it  
	6. admin will be able to see  graph based result per questions for given poll.  
	7. super admins will be able to review and activate poll if it satisfies content policies.  
	8. system should have monitoring system to give health of system and various services, also should  generate alerts about failures/load/performance.  
	
2. System interface definition  
	1. Security related interface 
		 authentication()  
         authorization()  
	         
	2. User related interface  
		crudOpForUser(User user)  
	 
	3. Poll related interface  
		  addPoll(String userId, Poll poll)  
		loadMyPolls(String userId)  
loadPublicPolls()  
loadResults(Poll poll)  
selectChoice(String questionId, String choiseId, String userId)  
  
  
  3. Back-of-the-envelope estimation  
there can be millions of users/polls in system.  

 4.  Model  For System
 
        `USER: ID, FULL_NAME, USERNAME, EMAIL, PASSWORD, CREATED_AT, 		UPDATED_AT `  
        `POLL: ID, USER_ID, NAME, IS_PUBLIC, IS_ACTIVE, EXPIRATION_DATE_TIME, CREATED_BY, UPDATED_BY,CREATED_AT, UPDATED_AT`  
        `QUESTION : ID, POLL_ID, VALUE`  
        `CHOICE : ID, QUESTION_ID, VALUE`  
        `VOTE : ID, QUESTION_ID, CHOCE_ID, USER_ID`
 
            *(mysql db will be used)
  
  
  
 5. High-level design  
 
todo: add block diagram

  6. Detailed design   (To Do)
		1. How we will handle hot polls  
		2. Where should we introduce cache to speed up things  
		3. what are some bottlenecks in system and what can be done about those.  
		4. What components need better load balancing?  
  
