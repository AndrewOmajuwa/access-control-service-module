insert into user_key_cloak (uuid, email, first_name, last_name)
values ('48553c16-56e4-42e6-8cf4-25cee7609a33', 'admin@user', 'admin', 'user');
insert into profile (name)
values ('admin');
insert into user_profile (profile_id, userkeycloak_id)
values (1, '48553c16-56e4-42e6-8cf4-25cee7609a33');
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

insert into business_function (application_name, function_name)
values ('doctor-service', 'user');

insert into business_function (application_name, function_name)
values ('doctor-service', 'profile');

insert into permission (name)
values ('delete');

insert into permission (name)
values ('update');

insert into profile (name)
values ('test');

insert into profile (name)
values ('testDelete');

insert into business_function_permission (business_function_id, permission_id)
values (7, 2);

insert into user_profile (profile_id, userkeycloak_id)
values (2, '48553c16-56e4-42e6-8cf4-25cee7609a33');

