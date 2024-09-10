--------------------------------------------------------
--  File created - Wednesday-September-11-2024   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table USERS
--------------------------------------------------------

  CREATE TABLE "DEP_MU"."USERS" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(100 BYTE), 
	"EMAIL" VARCHAR2(100 BYTE), 
	"COUNTRY" VARCHAR2(100 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
REM INSERTING into DEP_MU.USERS
SET DEFINE OFF;
Insert into DEP_MU.USERS (ID,NAME,EMAIL,COUNTRY) values (41,'Usama','usama@example.com','Pakistan');
Insert into DEP_MU.USERS (ID,NAME,EMAIL,COUNTRY) values (43,'Elon Musk','elon.musk@gmail.com','USA');
Insert into DEP_MU.USERS (ID,NAME,EMAIL,COUNTRY) values (44,'Shahbaz Khan','shahbaz@gmail.com','Pakistan');
--------------------------------------------------------
--  DDL for Index SYS_C007035
--------------------------------------------------------

  CREATE UNIQUE INDEX "DEP_MU"."SYS_C007035" ON "DEP_MU"."USERS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Trigger USER_ID_TRIGGER
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "DEP_MU"."USER_ID_TRIGGER" 
BEFORE INSERT ON users
FOR EACH ROW
 WHEN (NEW.id IS NULL) BEGIN
  SELECT user_seq.NEXTVAL INTO :NEW.id FROM dual;
END;

/
ALTER TRIGGER "DEP_MU"."USER_ID_TRIGGER" ENABLE;
--------------------------------------------------------
--  Constraints for Table USERS
--------------------------------------------------------

  ALTER TABLE "DEP_MU"."USERS" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
