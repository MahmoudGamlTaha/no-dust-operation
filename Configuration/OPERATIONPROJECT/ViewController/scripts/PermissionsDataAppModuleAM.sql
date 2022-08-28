

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'ActionCategory.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='ActionCategory.view' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'ActionCategory.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ActionCategory.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ActionCategory.create' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'ActionCategory.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ActionCategory.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ActionCategory.update'  
						 and MODULE_ID=20 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'ActionCategory.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ActionCategory.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ActionCategory.delete' 
						 and MODULE_ID=20);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'Address.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='Address.view' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'Address.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'Address.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='Address.create' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'Address.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'Address.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='Address.update'  
						 and MODULE_ID=20 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'Address.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'Address.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='Address.delete' 
						 and MODULE_ID=20);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'CustomerProfile.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='CustomerProfile.view' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'CustomerProfile.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'CustomerProfile.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='CustomerProfile.create' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'CustomerProfile.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'CustomerProfile.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='CustomerProfile.update'  
						 and MODULE_ID=20 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'CustomerProfile.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'CustomerProfile.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='CustomerProfile.delete' 
						 and MODULE_ID=20);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'ContractManagment.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='ContractManagment.view' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'ContractManagment.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ContractManagment.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ContractManagment.create' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'ContractManagment.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ContractManagment.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ContractManagment.update'  
						 and MODULE_ID=20 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'ContractManagment.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ContractManagment.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ContractManagment.delete' 
						 and MODULE_ID=20);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'FinancialPeriod.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='FinancialPeriod.view' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'FinancialPeriod.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'FinancialPeriod.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='FinancialPeriod.create' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'FinancialPeriod.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'FinancialPeriod.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='FinancialPeriod.update'  
						 and MODULE_ID=20 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'FinancialPeriod.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'FinancialPeriod.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='FinancialPeriod.delete' 
						 and MODULE_ID=20);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'ddd.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='ddd.view' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'ddd.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ddd.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ddd.create' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'ddd.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ddd.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ddd.update'  
						 and MODULE_ID=20 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'ddd.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'ddd.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='ddd.delete' 
						 and MODULE_ID=20);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'OperationSchedule.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='OperationSchedule.view' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'OperationSchedule.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'OperationSchedule.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='OperationSchedule.create' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'OperationSchedule.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'OperationSchedule.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='OperationSchedule.update'  
						 and MODULE_ID=20 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'OperationSchedule.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'OperationSchedule.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='OperationSchedule.delete' 
						 and MODULE_ID=20);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'Package.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='Package.view' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'Package.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'Package.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='Package.create' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'Package.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'Package.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='Package.update'  
						 and MODULE_ID=20 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'Package.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'Package.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='Package.delete' 
						 and MODULE_ID=20);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'CollectionPlanType.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='CollectionPlanType.view' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'CollectionPlanType.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'CollectionPlanType.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='CollectionPlanType.create' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'CollectionPlanType.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'CollectionPlanType.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='CollectionPlanType.update'  
						 and MODULE_ID=20 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'CollectionPlanType.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'CollectionPlanType.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='CollectionPlanType.delete' 
						 and MODULE_ID=20);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'PointCatalog.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='PointCatalog.view' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'PointCatalog.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'PointCatalog.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='PointCatalog.create' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'PointCatalog.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'PointCatalog.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='PointCatalog.update'  
						 and MODULE_ID=20 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'PointCatalog.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'PointCatalog.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='PointCatalog.delete' 
						 and MODULE_ID=20);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'SchedulingExcludeReason.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='SchedulingExcludeReason.view' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'SchedulingExcludeReason.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'SchedulingExcludeReason.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='SchedulingExcludeReason.create' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'SchedulingExcludeReason.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'SchedulingExcludeReason.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='SchedulingExcludeReason.update'  
						 and MODULE_ID=20 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'SchedulingExcludeReason.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'SchedulingExcludeReason.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='SchedulingExcludeReason.delete' 
						 and MODULE_ID=20);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'TemplateConfiguration.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='TemplateConfiguration.view' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'TemplateConfiguration.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'TemplateConfiguration.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='TemplateConfiguration.create' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'TemplateConfiguration.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'TemplateConfiguration.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='TemplateConfiguration.update'  
						 and MODULE_ID=20 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'TemplateConfiguration.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'TemplateConfiguration.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='TemplateConfiguration.delete' 
						 and MODULE_ID=20);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'Users.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='Users.view' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'Users.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'Users.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='Users.create' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'Users.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'Users.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='Users.update'  
						 and MODULE_ID=20 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'Users.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'Users.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='Users.delete' 
						 and MODULE_ID=20);

         insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'Products.view'
       from dual
       where not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS
                         where PERMISSION_NAME='Products.view' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'Products.create'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'Products.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='Products.create' and MODULE_ID=20);

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'Products.update'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'Products.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='Products.update'  
						 and MODULE_ID=20 );

       insert into SD_SUBSYS_MODULEPERMISSONS (PERMISSION_ID, MODULE_ID, PERMISSION_NAME) 
       select SD_SEQ.nextval, 20, 'Products.delete'
       from SD_SUBSYS_MODULEPERMISSONS PMN
       where PERMISSION_NAME = 'Products.view' and PMN.MODULE_ID=20
       and not exists (select '1' from SD_SUBSYS_MODULEPERMISSONS 
                         where PERMISSION_NAME='Products.delete' 
						 and MODULE_ID=20);

  
-- Assign all permissions to Administrator role
insert into SC_ROLESUBSYS(ROLE_ID,SUBSYS_ID)
SELECT 1,6 FROM DUAL WHERE NOT EXISTS (SELECT '1' FROM SC_ROLESUBSYS WHERE ROLE_ID=1 AND SUBSYS_ID=6);

insert into SC_ROLESUBSYS_MOD(ROLE_ID,SUBSYS_ID,MODULE_ID)
SELECT 1,6,20 FROM DUAL WHERE NOT EXISTS (SELECT '1' FROM SC_ROLESUBSYS_MOD WHERE ROLE_ID=1 AND SUBSYS_ID=6 AND MODULE_ID=20);

insert into SC_ROLESUBMOD_PERMISSIONS (ROLE_ID,PERMISSION_ID,MODULE_ID,SUBSYS_ID)
select 1,PERMISSION_ID , MODULE_ID,6
from SD_SUBSYS_MODULEPERMISSONS PMN
where MODULE_ID=20
and  not exists (select '1' 
                 from SC_ROLESUBMOD_PERMISSIONS
                 where ROLE_ID = 1
                 and PERMISSION_ID = PMN.PERMISSION_ID
                 and MODULE_ID = PMN.MODULE_ID
                 and SUBSYS_ID = 6);  