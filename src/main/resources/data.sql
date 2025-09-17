INSERT INTO PROJECT_BOARD (id, name) VALUES (RANDOM_UUID(), 'Board A');

INSERT INTO PROJECT_STATUS (id, name, project_board_id) VALUES(RANDOM_UUID(), 'NEW', (SELECT id FROM PROJECT_BOARD WHERE name = 'Board A'));
INSERT INTO PROJECT_STATUS (id, name, project_board_id) VALUES(RANDOM_UUID(), 'IN PROCESS', (SELECT id FROM PROJECT_BOARD WHERE name = 'Board A'));
INSERT INTO PROJECT_STATUS (id, name, project_board_id) VALUES(RANDOM_UUID(), 'TESTING', (SELECT id FROM PROJECT_BOARD WHERE name = 'Board A'));
INSERT INTO PROJECT_STATUS (id, name, project_board_id) VALUES(RANDOM_UUID(), 'DONE', (SELECT id FROM PROJECT_BOARD WHERE name = 'Board A'));

INSERT INTO PROJECT_TASK (id, name, project_status_id) VALUES(RANDOM_UUID(), 'Wash Dishes', (SELECT id FROM PROJECT_STATUS WHERE name = 'Done'));
INSERT INTO PROJECT_TASK (id, name, project_status_id) VALUES(RANDOM_UUID(), 'Do Laundry', (SELECT id FROM PROJECT_STATUS WHERE name = 'TESTING'));
INSERT INTO PROJECT_TASK (id, name, project_status_id) VALUES(RANDOM_UUID(), 'Brush Teeth', (SELECT id FROM PROJECT_STATUS WHERE name = 'IN PROCESS'));
INSERT INTO PROJECT_TASK (id, name, project_status_id) VALUES(RANDOM_UUID(), 'Relax', (SELECT id FROM PROJECT_STATUS WHERE name = 'NEW'));
INSERT INTO PROJECT_TASK (id, name, project_status_id) VALUES(RANDOM_UUID(), 'Go To Bed', (SELECT id FROM PROJECT_STATUS WHERE name = 'NEW'));

INSERT INTO APP_USER (id, username) VALUES(RANDOM_UUID(), 'User');

INSERT INTO user_task (user_id, task_id) VALUES ((SELECT id FROM APP_USER WHERE username = 'User'), (SELECT id FROM PROJECT_TASK WHERE name = 'Go To Bed'));
INSERT INTO user_task (user_id, task_id) VALUES ((SELECT id FROM APP_USER WHERE username = 'User'), (SELECT id FROM PROJECT_TASK WHERE name = 'Relax'));
