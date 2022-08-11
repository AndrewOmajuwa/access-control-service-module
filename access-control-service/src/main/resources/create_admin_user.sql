insert into profile (name)
values ('admin');
insert into user_profile (profile_id, userkeycloak_id)
values (1, 'insert uuid here');
insert into business_function (application_name, function_name)
values ('access-control-service', 'permission');
insert into permission (name)
values ('create');
insert into business_function_permission (business_function_id, permission_id)
values (1, 1);
insert into profile_business_function_permission (business_function_permission_id, profile_id)
values (1, 1);
insert into business_function (application_name, function_name)
values ('access-control-service', 'business-function');
insert into business_function_permission (business_function_id, permission_id)
values (2, 1);
insert into profile_business_function_permission (business_function_permission_id, profile_id)
values (2, 1);

insert into business_function (application_name, function_name)
values ('access-control-service', 'business-function-permissions');
insert into business_function_permission (business_function_id, permission_id)
values (3, 1);
insert into profile_business_function_permission (business_function_permission_id, profile_id)
values (3, 1);

insert into business_function (application_name, function_name)
values ('access-control-service', 'profile');
insert into business_function_permission (business_function_id, permission_id)
values (4, 1);
insert into profile_business_function_permission (business_function_permission_id, profile_id)
values (4, 1);

insert into business_function (application_name, function_name)
values ('access-control-service', 'profile-business-function-permissions');
insert into business_function_permission (business_function_id, permission_id)
values (5, 1);
insert into profile_business_function_permission (business_function_permission_id, profile_id)
values (5, 1);

insert into business_function (application_name, function_name)
values ('access-control-service', 'user-profiles');
insert into business_function_permission (business_function_id, permission_id)
values (6, 1);
insert into profile_business_function_permission (business_function_permission_id, profile_id)
values (6, 1);