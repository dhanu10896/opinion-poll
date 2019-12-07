Step 1: Requirements clarifications

1.system will support authentication, authorization, different user roles (google/facebook sign in, email verified username password authentication)
2.any user can will be able to create poll, with expirationDateTime
3.poll will have Multiple question with multiple options
4.link for for poll will be shared with users once poll becomes active
5.other users will be add poll to watchlist if poll is public or else only admin will have acess to it
6.admin will be able to see circular graph or bar graph of result for each question.
7.super admins will be able to review and activate poll if it satifies content policies.
8.system should have monitoring sytem to give health of system and various services, also should 
  generate alerts about failures/load/performace.

Step 2: System interface definition

Security related interface
authentication()
authorization()

User related interface
crudUser(User user)

Poll related interface

addPoll(String userId, Poll poll)
loadMyPolls(String userId)
loadPublicPolls()
loadResults(Poll poll)
selectChoice(String questionId, String choiseId, String userId)


Step 3: Back-of-the-envelope estimation
there can be millions of users/polls in system.

Step 4:
USER: ID, FULL_NAME, USERNAME, EMAIL, PASSWORD, CREATED_AT, UPDATED_AT
POLL: ID, USER_ID, NAME, IS_PUBLIC, IS_ACTIVE, EXPIRATION_DATE_TIME, CREATED_BY, UPDATED_BY,CREATED_AT, UPDATED_AT
QUESTION : ID, POLL_ID, VALUE
CHOISE : ID, QUESTION_ID, VALUE
VOTE : ID, QUESTION_ID, CHOSE_ID, USER_ID

mysql db will be used with jpa => keep queries as much as to jpa sytax for easy migration

Step 5: High-level design


user1 ->  {  			  .
user2 ->  { [loadbalancer]->read server/write/apirequest servers depending on traffic we are expecting [Database server]
user3  -> {  
==================================================== Monitor Everything =================================================                                                                               

Step 6: Detailed design
1. How we will handle hot polls
2. Where should we introduce cache to speed up things
3. what are some bottlenecks in system and what can be done about those.
4. What components need better load balancing?

Step 7: Identifying and resolving bottlenecks
1.Is there any single point of failure in our system? What are we doing to mitigate it?
2.Do we’ve enough replicas of the data so that if we lose a few servers, we can still serve our users?
3.Similarly, do we’ve enough copies of different services running, such that a few failures will not
cause total system shutdown?
4.How are we monitoring the performance of our service? Do we get alerts whenever critical
components fail or their performance degrade?

- yeah if our single db server fails wholse system comes down, also if we have specialized servers for to handle 
  specific request then those also need some slave servers to avoid system from going down.
- load balancer also might fail so we might need more that one
- wherever possible we will increase options of replicating services and data to avoid signle point failure.
