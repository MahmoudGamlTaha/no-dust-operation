

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'WeekDaysVO.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='WeekDaysVO.view' and MODULE_ID=1);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'WeekDaysVO.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'WeekDaysVO.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='WeekDaysVO.create' and MODULE_ID=1);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'WeekDaysVO.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'WeekDaysVO.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='WeekDaysVO.update'  
						 and MODULE_ID=1 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'WeekDaysVO.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'WeekDaysVO.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='WeekDaysVO.delete' 
						 and MODULE_ID=1);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'WeekDaysAgentVO.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='WeekDaysAgentVO.view' and MODULE_ID=1);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'WeekDaysAgentVO.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'WeekDaysAgentVO.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='WeekDaysAgentVO.create' and MODULE_ID=1);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'WeekDaysAgentVO.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'WeekDaysAgentVO.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='WeekDaysAgentVO.update'  
						 and MODULE_ID=1 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'WeekDaysAgentVO.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'WeekDaysAgentVO.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='WeekDaysAgentVO.delete' 
						 and MODULE_ID=1);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'AgentAreasVO.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='AgentAreasVO.view' and MODULE_ID=1);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'AgentAreasVO.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'AgentAreasVO.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='AgentAreasVO.create' and MODULE_ID=1);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'AgentAreasVO.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'AgentAreasVO.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='AgentAreasVO.update'  
						 and MODULE_ID=1 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'AgentAreasVO.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'AgentAreasVO.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='AgentAreasVO.delete' 
						 and MODULE_ID=1);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'WeekDaysSupervisorVO.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='WeekDaysSupervisorVO.view' and MODULE_ID=1);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'WeekDaysSupervisorVO.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'WeekDaysSupervisorVO.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='WeekDaysSupervisorVO.create' and MODULE_ID=1);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'WeekDaysSupervisorVO.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'WeekDaysSupervisorVO.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='WeekDaysSupervisorVO.update'  
						 and MODULE_ID=1 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'WeekDaysSupervisorVO.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'WeekDaysSupervisorVO.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='WeekDaysSupervisorVO.delete' 
						 and MODULE_ID=1);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'AccountingTeamLeader.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='AccountingTeamLeader.view' and MODULE_ID=1);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'AccountingTeamLeader.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'AccountingTeamLeader.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='AccountingTeamLeader.create' and MODULE_ID=1);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'AccountingTeamLeader.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'AccountingTeamLeader.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='AccountingTeamLeader.update'  
						 and MODULE_ID=1 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'AccountingTeamLeader.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'AccountingTeamLeader.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='AccountingTeamLeader.delete' 
						 and MODULE_ID=1);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'AccountingAgent.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='AccountingAgent.view' and MODULE_ID=1);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'AccountingAgent.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'AccountingAgent.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='AccountingAgent.create' and MODULE_ID=1);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'AccountingAgent.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'AccountingAgent.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='AccountingAgent.update'  
						 and MODULE_ID=1 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'AccountingAgent.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'AccountingAgent.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='AccountingAgent.delete' 
						 and MODULE_ID=1);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'AccountingDispatchSupervisor.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='AccountingDispatchSupervisor.view' and MODULE_ID=1);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'AccountingDispatchSupervisor.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'AccountingDispatchSupervisor.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='AccountingDispatchSupervisor.create' and MODULE_ID=1);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'AccountingDispatchSupervisor.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'AccountingDispatchSupervisor.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='AccountingDispatchSupervisor.update'  
						 and MODULE_ID=1 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 1, 'AccountingDispatchSupervisor.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'AccountingDispatchSupervisor.view' and PMN.MODULE_ID=1
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='AccountingDispatchSupervisor.delete' 
						 and MODULE_ID=1);

  
-- Assign all permissions to Administrator role
insert into SC_ROLESUBSYS(ROLE_ID,SUBSYS_ID)
SELECT 1,1 FROM DUAL WHERE NOT EXISTS (SELECT '1' FROM SC_ROLESUBSYS WHERE ROLE_ID=1 AND SUBSYS_ID=1);

insert into SC_ROLESUBSYS_MOD(ROLE_ID,SUBSYS_ID,MODULE_ID)
SELECT 1,1,2 FROM DUAL WHERE NOT EXISTS (SELECT '1' FROM SC_ROLESUBSYS_MOD WHERE ROLE_ID=1 AND SUBSYS_ID=1 AND MODULE_ID=1);

insert into SC_ROLESUBMOD_PERMISSIONS (ROLE_ID,PERMISSION_ID,MODULE_ID,SUBSYS_ID)
select 1,PERMISSION_ID , MODULE_ID,1
from SD_SUBSYS_MODULEPERMISSONS PMN
where MODULE_ID=1
and  not exists (select '1' 
                 from SC_ROLESUBMOD_PERMISSIONS
                 where ROLE_ID = 1
                 and PERMISSION_ID = PMN.PERMISSION_ID
                 and MODULE_ID = PMN.MODULE_ID
                 and SUBSYS_ID = 1);  