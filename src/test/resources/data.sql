-- Insert 10 popular JS frameworks into the database.
-- Include some random versions with random EOS dates
-- Last two entries differ a little bit (null EOS, no versions)

INSERT INTO javascript_framework (name, rating) VALUES ('Aurelia', 4.2);
SET @ID_AURELIA = (SELECT ID FROM javascript_framework WHERE NAME='Aurelia');
INSERT INTO framework_version (framework_id, version, end_of_support) VALUES (@ID_AURELIA, 1, '2023-05-23'), (@ID_AURELIA, 2, '2023-06-21'), (@ID_AURELIA, 3, '2023-07-02');

INSERT INTO javascript_framework (name, rating) VALUES ('Meteor', 4.4);
SET @ID_METEOR = (SELECT ID FROM javascript_framework WHERE NAME='Meteor');
INSERT INTO framework_version (framework_id, version, end_of_support) VALUES (@ID_METEOR, 1, '2022-11-01'), (@ID_METEOR, 2, '2024-03-11'), (@ID_METEOR, 3, '2024-07-04');

INSERT INTO javascript_framework (name, rating) VALUES ('Vue.js', 4.7);
SET @ID_VUE = (SELECT ID FROM javascript_framework WHERE NAME='Vue.js');
INSERT INTO framework_version (framework_id, version, end_of_support) VALUES (@ID_VUE, 1, '2021-05-23'), (@ID_VUE, 2, '2022-06-21'), (@ID_VUE, 3, '2023-08-02');

INSERT INTO javascript_framework (name, rating) VALUES ('Mithril', 4.3);
SET @ID_MITHRIL = (SELECT ID FROM javascript_framework WHERE NAME='Mithril');
INSERT INTO framework_version (framework_id, version, end_of_support) VALUES (@ID_MITHRIL, 1, '2021-11-01'), (@ID_MITHRIL, 2, '2024-01-11'), (@ID_MITHRIL, 3, '2025-01-01');

INSERT INTO javascript_framework (name, rating) VALUES ('Angular', 4.9);
SET @ID_ANGULAR = (SELECT ID FROM javascript_framework WHERE NAME='Angular');
INSERT INTO framework_version (framework_id, version, end_of_support) VALUES (@ID_ANGULAR, 1, '2022-11-04'), (@ID_ANGULAR, 2, '2024-03-14'), (@ID_ANGULAR, 3, '2024-07-04');

INSERT INTO javascript_framework (name, rating) VALUES ('Ember.js', 4.46);
SET @ID_EMBER = (SELECT ID FROM javascript_framework WHERE NAME='Ember.js');
INSERT INTO framework_version (framework_id, version, end_of_support) VALUES (@ID_EMBER, 1, '2022-11-01'), (@ID_EMBER, 2, '2024-03-11'), (@ID_EMBER, 3, '2024-07-03');

INSERT INTO javascript_framework (name, rating) VALUES ('Node.js', 4.6);
SET @ID_NODE = (SELECT ID FROM javascript_framework WHERE NAME='Node.js');
INSERT INTO framework_version (framework_id, version, end_of_support) VALUES (@ID_NODE, 1, '2022-11-08'), (@ID_NODE, 2, '2024-03-18'), (@ID_NODE, 3, '2024-07-08');

INSERT INTO javascript_framework (name, rating) VALUES ('Polymer', 4.2);
SET @ID_POLYMER = (SELECT ID FROM javascript_framework WHERE NAME='Polymer');
INSERT INTO framework_version (framework_id, version, end_of_support) VALUES (@ID_POLYMER, 1, '2022-11-08'), (@ID_POLYMER, 2, '2024-03-18'), (@ID_POLYMER, 3, '2024-07-08');

INSERT INTO javascript_framework (name, rating) VALUES ('React', 4.8);
SET @ID_REACT = (SELECT ID FROM javascript_framework WHERE NAME='React');
INSERT INTO framework_version (framework_id, version, end_of_support) VALUES (@ID_REACT, 1, null), (@ID_REACT, 2, '2024-03-18');

INSERT INTO javascript_framework (id,name,rating) VALUES (123,'Backbone.js',4.2);
