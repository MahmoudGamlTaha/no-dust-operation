

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'PostTeamleader.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='PostTeamleader.view' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'PostTeamleader.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'PostTeamleader.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='PostTeamleader.create' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'PostTeamleader.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'PostTeamleader.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='PostTeamleader.update'  
						 and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'PostTeamleader.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'PostTeamleader.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='PostTeamleader.delete' 
						 and MODULE_ID=22);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamLeaderScreen.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='DataTeamLeaderScreen.view' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamLeaderScreen.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamLeaderScreen.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamLeaderScreen.create' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamLeaderScreen.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamLeaderScreen.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamLeaderScreen.update'  
						 and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamLeaderScreen.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamLeaderScreen.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamLeaderScreen.delete' 
						 and MODULE_ID=22);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'PostScreen.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='PostScreen.view' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'PostScreen.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'PostScreen.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='PostScreen.create' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'PostScreen.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'PostScreen.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='PostScreen.update'  
						 and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'PostScreen.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'PostScreen.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='PostScreen.delete' 
						 and MODULE_ID=22);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgent.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='DataTeamAgent.view' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgent.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgent.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgent.create' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgent.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgent.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgent.update'  
						 and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgent.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgent.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgent.delete' 
						 and MODULE_ID=22);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentPendingWork.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='DataTeamAgentPendingWork.view' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentPendingWork.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentPendingWork.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentPendingWork.create' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentPendingWork.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentPendingWork.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentPendingWork.update'  
						 and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentPendingWork.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentPendingWork.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentPendingWork.delete' 
						 and MODULE_ID=22);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'ProductContractDDownloadVO.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='ProductContractDDownloadVO.view' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'ProductContractDDownloadVO.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ProductContractDDownloadVO.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ProductContractDDownloadVO.create' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'ProductContractDDownloadVO.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ProductContractDDownloadVO.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ProductContractDDownloadVO.update'  
						 and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'ProductContractDDownloadVO.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ProductContractDDownloadVO.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ProductContractDDownloadVO.delete' 
						 and MODULE_ID=22);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentDashboard.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='DataTeamAgentDashboard.view' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentDashboard.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentDashboard.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentDashboard.create' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentDashboard.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentDashboard.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentDashboard.update'  
						 and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentDashboard.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentDashboard.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentDashboard.delete' 
						 and MODULE_ID=22);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentNotesReview.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='DataTeamAgentNotesReview.view' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentNotesReview.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentNotesReview.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentNotesReview.create' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentNotesReview.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentNotesReview.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentNotesReview.update'  
						 and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentNotesReview.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentNotesReview.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentNotesReview.delete' 
						 and MODULE_ID=22);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentEndOfDayReview.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='DataTeamAgentEndOfDayReview.view' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentEndOfDayReview.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentEndOfDayReview.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentEndOfDayReview.create' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentEndOfDayReview.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentEndOfDayReview.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentEndOfDayReview.update'  
						 and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentEndOfDayReview.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentEndOfDayReview.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentEndOfDayReview.delete' 
						 and MODULE_ID=22);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentContractReview.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='DataTeamAgentContractReview.view' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentContractReview.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentContractReview.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentContractReview.create' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentContractReview.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentContractReview.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentContractReview.update'  
						 and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentContractReview.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentContractReview.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentContractReview.delete' 
						 and MODULE_ID=22);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'NewCancelSalesContractProducts.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='NewCancelSalesContractProducts.view' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'NewCancelSalesContractProducts.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'NewCancelSalesContractProducts.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='NewCancelSalesContractProducts.create' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'NewCancelSalesContractProducts.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'NewCancelSalesContractProducts.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='NewCancelSalesContractProducts.update'  
						 and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'NewCancelSalesContractProducts.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'NewCancelSalesContractProducts.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='NewCancelSalesContractProducts.delete' 
						 and MODULE_ID=22);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentCarReconciliation.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='DataTeamAgentCarReconciliation.view' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentCarReconciliation.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentCarReconciliation.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentCarReconciliation.create' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentCarReconciliation.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentCarReconciliation.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentCarReconciliation.update'  
						 and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'DataTeamAgentCarReconciliation.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'DataTeamAgentCarReconciliation.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='DataTeamAgentCarReconciliation.delete' 
						 and MODULE_ID=22);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'PilotCoverGroup.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='PilotCoverGroup.view' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'PilotCoverGroup.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'PilotCoverGroup.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='PilotCoverGroup.create' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'PilotCoverGroup.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'PilotCoverGroup.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='PilotCoverGroup.update'  
						 and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'PilotCoverGroup.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'PilotCoverGroup.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='PilotCoverGroup.delete' 
						 and MODULE_ID=22);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'ProductDetailsGroup.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='ProductDetailsGroup.view' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'ProductDetailsGroup.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ProductDetailsGroup.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ProductDetailsGroup.create' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'ProductDetailsGroup.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ProductDetailsGroup.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ProductDetailsGroup.update'  
						 and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'ProductDetailsGroup.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ProductDetailsGroup.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ProductDetailsGroup.delete' 
						 and MODULE_ID=22);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'ProductLostRlost.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='ProductLostRlost.view' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'ProductLostRlost.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ProductLostRlost.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ProductLostRlost.create' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'ProductLostRlost.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ProductLostRlost.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ProductLostRlost.update'  
						 and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'ProductLostRlost.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ProductLostRlost.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ProductLostRlost.delete' 
						 and MODULE_ID=22);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'HubCoverGroup.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='HubCoverGroup.view' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'HubCoverGroup.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'HubCoverGroup.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='HubCoverGroup.create' and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'HubCoverGroup.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'HubCoverGroup.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='HubCoverGroup.update'  
						 and MODULE_ID=22);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 22, 'HubCoverGroup.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'HubCoverGroup.view' and PMN.MODULE_ID=22
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='HubCoverGroup.delete' 
						 and MODULE_ID=22);

  
-- Assign all permissions to Administrator role
insert into SC_ROLESUBSYS(ROLE_ID,SUBSYS_ID)
SELECT 1,1 FROM DUAL WHERE NOT EXISTS (SELECT '1' FROM SC_ROLESUBSYS WHERE ROLE_ID=1 AND SUBSYS_ID=6);

insert into SC_ROLESUBSYS_MOD(ROLE_ID,SUBSYS_ID,MODULE_ID)
SELECT 1,6,22 FROM DUAL WHERE NOT EXISTS (SELECT '1' FROM SC_ROLESUBSYS_MOD WHERE ROLE_ID=1 AND SUBSYS_ID=6 AND MODULE_ID=22);

insert into SC_ROLESUBMOD_PERMISSIONS (ROLE_ID,PERMISSION_ID,MODULE_ID,SUBSYS_ID)
select 1,PERMISSION_ID , MODULE_ID,6
from SD_SUBSYS_MODULEPERMISSONS PMN
where MODULE_ID=22
and  not exists (select '1' 
                 from SC_ROLESUBMOD_PERMISSIONS
                 where ROLE_ID = 1
                 and PERMISSION_ID = PMN.PERMISSION_ID
                 and MODULE_ID = PMN.MODULE_ID
                 and SUBSYS_ID = 6);  