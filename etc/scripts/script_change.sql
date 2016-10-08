ALTER TABLE `motivate`.`profile` ADD COLUMN `active` TINYINT(1) DEFAULT 1 AFTER `publicProfile`,
 ADD COLUMN `confirmationNumber` BIGINT AFTER `active`;
ALTER TABLE `motivate`.`file` ADD COLUMN `idx` INTEGER AFTER `PROFILE_ID`; 
Update file set idx=0;
ALTER TABLE `motivate`.`event` ADD COLUMN `mapLatitude` DOUBLE AFTER `startDate`,
 ADD COLUMN `mapLongitude` DOUBLE AFTER `mapLatitude`;
ALTER TABLE `motivate`.`questionaire` ADD COLUMN `questionaireVersion` VARCHAR(45) AFTER `QUESTIONAIRE_ID`;
ALTER TABLE `motivate`.`tag` ADD COLUMN `version` VARCHAR(45) AFTER `graphValueNegative`;
ALTER TABLE `motivate`.`tag` ADD COLUMN `questionLink` VARCHAR(45) AFTER `version`;
ALTER TABLE `motivate`.`tag` ADD COLUMN `tagAlias` VARCHAR(45) AFTER `questionLink`;
ALTER TABLE `motivate`.`order_items` ADD COLUMN `itemDate` DATETIME AFTER `quantity`;
ALTER TABLE `motivate`.`order_items` ADD COLUMN `itemName` VARCHAR(255) AFTER `itemDate`;
ALTER TABLE `motivate`.`order_items` ADD COLUMN `location` VARCHAR(255) AFTER `itemName`;
ALTER TABLE `motivate`.`forum` ADD COLUMN `description` VARCHAR(255) AFTER `topic`;
create table testimonies (TESTIMONY_ID integer not null auto_increment, content varchar(255), PROFILE_ID integer, primary key (TESTIMONY_ID))
alter table testimonies add column date datetime
alter table testimonies add index FKF81F25222D076C4F (PROFILE_ID), add constraint FKF81F25222D076C4F foreign key (PROFILE_ID) references profile (PROFILE_ID)
alter table testimonies ADD COLUMN event VARCHAR(265) AFTER `date`;

ALTER TABLE `motivate`.`profile` ADD COLUMN `presentationContent` TEXT AFTER `profileCreation`,
 ADD COLUMN `presenter` TINYINT DEFAULT 0 AFTER `presentationContent`;
 
ALTER TABLE `motivate`.`profile` ADD COLUMN `presentationTitle` VARCHAR(255) AFTER `presenter`;


create table podcast (PODCAST_ID integer not null auto_increment, description varchar(255), duration integer, fileLength integer, htmlLink varchar(255), link varchar(255), publishedDate datetime, subtitle varchar(255), title varchar(255), primary key (PODCAST_ID));


alter table cart_item add column EVENT_ID integer;
alter table tag_weight add column EVENT_ID integer;
alter table cart_item add index FKCADACD273E53810 (EVENT_ID), add constraint FKCADACD273E53810 foreign key (EVENT_ID) references event (EVENT_ID);
alter table tag_weight add index FK1D0F9EBD73E53810 (EVENT_ID), add constraint FK1D0F9EBD73E53810 foreign key (EVENT_ID) references event (EVENT_ID);

----------------------------Video Section--------------------------------------
---
----Added table video on June 13,2009  
---
DROP TABLE IF EXISTS `Category`;
CREATE TABLE `Category` (
     `CATEGORY_ID` int(11) NOT NULL auto_increment,
     `categoryName` varchar(255) default NULL,
     `categoryDescription` varchar(255) default NULL,
     PRIMARY KEY (`CATEGORY_ID`)
);

INSERT INTO  Category (categoryName, categoryDescription) VALUES  ('Cars','All types of Cars');

INSERT INTO   Category ( categoryName,categoryDescription) VALUES ('Fashion',  'All types of fashion');


DROP TABLE IF EXISTS `Video`;
CREATE TABLE `Video` (
     `VIDEO_ID` int(11) NOT NULL auto_increment,
     `videoDescription` varchar(255) default NULL,
     `IMAGEFILE_ID` int(11) default NULL,
     `creationDate` datetime default NULL,
     `views` int(11) default 0,
	 `videoPath` varchar(255) NOT NULL,
     `videoTitle` varchar(255) NOT NULL,
     `CATEGORY_TYPE` int(11) NOT NULL,
     PRIMARY KEY (`VIDEO_ID`),
	 KEY `category_key` (`CATEGORY_TYPE`),
     CONSTRAINT `category_key` FOREIGN KEY (`CATEGORY_TYPE`) REFERENCES `Category` (`CATEGORY_ID`),
     KEY `image_file_key` (`IMAGEFILE_ID`),
     CONSTRAINT `image_file_key` FOREIGN KEY (`IMAGEFILE_ID`) REFERENCES `file` (`FILE_ID`)
);


DROP TABLE IF EXISTS `video_comment`;
CREATE TABLE `video_comment` (
    `VIDEO_COMMENT_ID` int(11) NOT NULL auto_increment,
    `content` text,
    `creationDate` datetime default NULL,
    `VIDEO_ID` int(11) default NULL,
    `PROFILE_ID` int(11) default NULL,
    `name` varchar(255) default NULL,
     PRIMARY KEY  (`VIDEO_COMMENT_ID`),
     KEY `video_key` (`VIDEO_ID`),
     KEY `profile_key` (`PROFILE_ID`),
     CONSTRAINT `video_fk` FOREIGN KEY (`VIDEO_ID`) REFERENCES `video` (`VIDEO_ID`),
     CONSTRAINT `profile_fk` FOREIGN KEY (`PROFILE_ID`) REFERENCES `profile` (`PROFILE_ID`)
);


DROP TABLE IF EXISTS `i_like_video`;
CREATE TABLE `i_like_video` (
 `I_LIKE_VIDEO_ID` int(11) NOT NULL AUTO_INCREMENT,
 `VIDEO_ID` int(11) NOT NULL,
 `PROFILE_ID` int(11) NOT NULL,
 `createDate` date,
  PRIMARY KEY (`I_LIKE_VIDEO_ID`),
  FOREIGN KEY (`VIDEO_ID`) REFERENCES `video` (`VIDEO_ID`),
  FOREIGN KEY (`PROFILE_ID`) REFERENCES `profile` (`PROFILE_ID`)
);

DROP TABLE IF EXISTS `event_history`;
CREATE TABLE `event_history` (
    `EVENT_HISTORY_ID` int(11) NOT NULL auto_increment,
    `PROFILE_ID` int(11) default NULL,
    `EVENT_ID` int(11) default NULL,
    `creationDate` datetime default NULL,
    PRIMARY KEY  (`EVENT_HISTORY_ID`),
    KEY `event_history_profile_key` (`PROFILE_ID`),
    KEY `event_history_event_key` (`EVENT_ID`),
    CONSTRAINT `event_history_event_fk` FOREIGN KEY (`EVENT_ID`) REFERENCES `event` (`EVENT_ID`),
    CONSTRAINT `event_history_profile_fk` FOREIGN KEY (`PROFILE_ID`) REFERENCES `profile` (`PROFILE_ID`)
);

alter table Video add column IMAGEFILE_ID_THUMB integer;
alter table Video add index FK4ED245B130A6E01 (IMAGEFILE_ID_THUMB), add constraint FK4ED245B130A6E01 foreign key (IMAGEFILE_ID_THUMB) references FILE (FILE_ID);


create table eventSuggestion (EVENTSUGGEST_ID integer not null auto_increment, comment varchar(255), country varchar(255), email varchar(255), fee integer, speaker varchar(255), state varchar(255), votes integer, primary key (EVENTSUGGEST_ID))
ALTER TABLE eventsuggestion ADD COLUMN `confirm` BOOLEAN DEFAULT 1 AFTER `votes`;

////Changes December///////////////////////////////////////////////////////////////

Change in Profile table
drop table questionaire
drop table answer

*create table answer (id integer not null auto_increment, answerNumber integer, textualAnswer varchar(255), QUESTION_ID integer, primary key (id));
alter table answer add index FKABCA3FBE9B8CB526 (QUESTION_ID), add constraint FKABCA3FBE9B8CB526 foreign key (QUESTION_ID) references question (QUESTION_ID)

create table question (QUESTION_ID integer not null auto_increment, questionDescription varchar(255), questionaireVersion varchar(255), PROFILE_ID integer, primary key (QUESTION_ID))
ALTER TABLE question ADD COLUMN questionPhoneId INTEGER AFTER PROFILE_ID;

create table profileQuestion (QUESTION_ID integer not null, PROFILE_ID integer not null);
alter table profileQuestion add index FKBE7F70AF2D076C4F (PROFILE_ID), add constraint FKBE7F70AF2D076C4F foreign key (PROFILE_ID) references profile (PROFILE_ID)
alter table profileQuestion add index FKBE7F70AF9B8CB526 (QUESTION_ID), add constraint FKBE7F70AF9B8CB526 foreign key (QUESTION_ID) references question (QUESTION_ID)

ALTER TABLE profile ADD COLUMN source VARCHAR(255) AFTER iphoneUser;
update profile set source = 'system';

create table twitterUserAuthentication (TWITTERUSER_ID integer not null auto_increment, theKey varchar(255), secret varchar(255), username varchar(255), PROFILE_ID integer, primary key (TWITTERUSER_ID));
alter table twitterUserAuthentication add index FK7C846AF699BA280A (PROFILE_ID), add constraint FK7C846AF699BA280A foreign key (PROFILE_ID) references profile (PROFILE_ID);

ALTER TABLE question ADD COLUMN latitude VARCHAR(255) AFTER questionPhoneId,
 ADD COLUMN longitude VARCHAR(255) AFTER latitude;
 
 ALTER TABLE question ADD COLUMN createdBy VARCHAR(255) AFTER longitude;
 
 ALTER TABLE question ADD COLUMN parent BOOLEAN AFTER createdBy;
 
 ALTER TABLE question MODIFY COLUMN QUESTION_ID INT(11) NOT NULL,  ADD COLUMN country VARCHAR(255), ADD COLUMN state VARCHAR(255) ;
 
 /////////////////////////////////Changes////////////////////////////////////////////////////////////////////
 
create table blogpost_crowd (BLOG_ID integer not null auto_increment, content varchar(255), description varchar(255), publishedDate datetime, title varchar(255), primary key (BLOG_ID))
create table comment_crowd (COMMENT_ID integer not null auto_increment, content varchar(255), date datetime, name varchar(255), website varchar(255), BLOGPOST_ID integer, primary key (COMMENT_ID))
alter table comment_crowd add index FKBD3066CDB6BB00D6 (BLOGPOST_ID), add constraint FKBD3066CDB6BB00D6 foreign key (BLOGPOST_ID) references blogpost_crowd (BLOG_ID)

/////////////////////////////Changes//////////////////////

ALTER TABLE `motivate`.`profile` ADD COLUMN `subscribed` BOOLEAN AFTER `source`;
update profile set subscribed = true;

////////////////////change//////////////

alter table questionComment add index FKA4C23B999B8CB526 (QUESTION_ID), add constraint FKA4C23B999B8CB526 foreign key (QUESTION_ID) references question (QUESTION_ID);


/////////////CHANGE MAP LAST CHANGES ///////////////


create table anotherAnswer (id integer not null auto_increment, answerContent varchar(255), date datetime, QUESTION_ID integer, primary key (id));
alter table anotherAnswer add index FK29C4A4A19B8CB526 (QUESTION_ID), add constraint FK29C4A4A19B8CB526 foreign key (QUESTION_ID) references question (QUESTION_ID);

/////////////////Change MOderator////////////

create table device (DEVICE_ID integer not null auto_increment, deviceId varchar(255), deviceName varchar(255), MODERATOR_ID integer, primary key (DEVICE_ID));
create table moderator (MODERATOR_ID integer not null auto_increment, deviceId varchar(255), PROFILE_ID integer, primary key (MODERATOR_ID));
alter table device add index FKB06B1E566A951E8A (MODERATOR_ID), add constraint FKB06B1E566A951E8A foreign key (MODERATOR_ID) references moderator (MODERATOR_ID);
alter table moderator add index FK8882A50599BA280A (PROFILE_ID), add constraint FK8882A50599BA280A foreign key (PROFILE_ID) references profile (PROFILE_ID);

create table answer_private (id integer not null auto_increment, answerNumber integer, textualAnswer varchar(255), QUESTIONPRIVATE_ID integer, primary key (id));
create table question_private (QUESTIONPRIVATE_ID integer not null auto_increment, country varchar(255), creationDate datetime, hasAnswers bit, latitude varchar(255), longitude varchar(255), parent bit not null, questionDescription varchar(255), questionPhoneId integer, state varchar(255), MODERATOR_ID integer, primary key (QUESTIONPRIVATE_ID));
alter table answer_private add index FK9DB66E02479C1CE (QUESTIONPRIVATE_ID), add constraint FK9DB66E02479C1CE foreign key (QUESTIONPRIVATE_ID) references question_private (QUESTIONPRIVATE_ID);
alter table question_private add index FK8D63C22A6A951E8A (MODERATOR_ID), add constraint FK8D63C22A6A951E8A foreign key (MODERATOR_ID) references moderator (MODERATOR_ID);

///////////////////Change facebook graph//////////////////////////////////////////////////////////////
create table active_user (ACTIVEUSER_ID integer not null auto_increment, gender varchar(255), location varchar(255), name varchar(255), uid bigint, MODERATOR_ID integer, primary key (ACTIVEUSER_ID));
create table friend (FRIEND_ID integer not null auto_increment, name varchar(255), uid bigint, ACTIVEUSER_ID integer, primary key (FRIEND_ID));
alter table active_user add index FKC15699646A951E8A (MODERATOR_ID), add constraint FKC15699646A951E8A foreign key (MODERATOR_ID) references moderator (MODERATOR_ID);
alter table friend add index FKB4860A9E9AABDF4A (ACTIVEUSER_ID), add constraint FKB4860A9E9AABDF4A foreign key (ACTIVEUSER_ID) references active_user (ACTIVEUSER_ID);
/////////////////////////////BUndle//////////////
alter table question add column BUNDLE_ID integer;
create table question_bundle (BUNDLE_ID integer not null auto_increment, description varchar(255),topic varchar(255), primary key (BUNDLE_ID));
alter table question add index FKBA823BE6DA35B22C (BUNDLE_ID), add constraint FKBA823BE6DA35B22C foreign key (BUNDLE_ID) references question_bundle (BUNDLE_ID);

create table answer_nominal (id integer not null auto_increment, answerNumber integer, textualAnswer varchar(255), weight integer, primary key (id))
create table answer_nominal (id integer not null auto_increment, answerNumber integer, textualAnswer varchar(255), weight integer, primary key (id))
create table answer_nominal (id integer not null auto_increment, answerNumber integer, textualAnswer varchar(255), weight integer, QUESTION_ID integer, primary key (id))
alter table question add column nominal bit
alter table question add column nominal bit
alter table question_bundle add column imageURL varchar(255);
alter table question_bundle add column productId varchar(255);
-------------------
alter table `question_bundle` add column `permaLink` varchar(255);
alter table `visual` add column `permaLink` varchar(255);

create table plan (id integer not null auto_increment, endDate datetime, startDate datetime, user_id integer, primary key (id));
alter table plan add index FK348B2912F66C5 (user_id), add constraint FK348B2912F66C5 foreign key (user_id) references user (USER_ID);
create table transaction (id integer not null auto_increment, authCode varchar(255), message varchar(255), orderId varchar(255), responseStr varchar(255), result varchar(255), transactionDate datetime, plan_id integer, primary key (id));
alter table transaction add index FK7FA0D2DEEC81C205 (plan_id), add constraint FK7FA0D2DEEC81C205 foreign key (plan_id) references plan (id);
======SCORE ++++++======

create table score (SCORE_ID integer not null auto_increment, bumpedProfiles varchar(255), latitude varchar(255), longitude varchar(255), score integer, PROFILE_ID integer, primary key (SCORE_ID));
alter table score add index FK6833E9299BA280A (PROFILE_ID), add constraint FK6833E9299BA280A foreign key (PROFILE_ID) references profile (PROFILE_ID);
create table huntguess (HUNT_ID integer not null auto_increment, characterGuess varchar(255), guessTime datetime, interrogatorId integer, interrogeeId integer, primary key (HUNT_ID))
ALTER TABLE `crowdscanner_dev`.`profile_info` ADD COLUMN `peopleHuntId` INTEGER AFTER `website`;

======HTML====

alter table huntguess add column PROFILE_ID integer;
alter table huntguess add index FK3C78662499BA280A (PROFILE_ID), add constraint FK3C78662499BA280A foreign key (PROFILE_ID) references profile (PROFILE_ID)
alter table score add column totalScore integer;

=======HUNTID=======================
alter table huntguess add column bundleId integer;
create table peoplehuntid (PEOPLEHUNT_ID integer not null auto_increment, bundleId integer, huntId integer, PROFILE_ID integer, primary key (PEOPLEHUNT_ID));
alter table peoplehuntid add index FK4FAC4B3D99BA280A (PROFILE_ID), add constraint FK4FAC4B3D99BA280A foreign key (PROFILE_ID) references profile (PROFILE_ID);
alter table peoplehuntid add column persona varchar(255);

-------HuntObject-------------------
alter table huntrating add column opting bit not null
alter table huntrating add column phoneNumber integer
ALTER TABLE `crowdscanner_dev`.`question` CHANGE COLUMN `questionaireVersion` `questionType` VARCHAR(255) NULL DEFAULT NULL  ;





