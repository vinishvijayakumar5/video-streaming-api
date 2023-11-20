CREATE TABLE video_metadata (
    id long NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    synopsis VARCHAR(255) NOT NULL,
    director VARCHAR(255) NOT NULL,
    caste VARCHAR(255) NOT NULL,
    year_of_release NUMERIC(4) NOT NULL,
    genre VARCHAR(255) NOT NULL,
    running_time VARCHAR(255) NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted NUMERIC(1),
    PRIMARY KEY (id)
);

CREATE TABLE video_content (
    id long NOT NULL AUTO_INCREMENT,
    source VARCHAR(255) NOT NULL,
    metadata_id long NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted NUMERIC(1),
    PRIMARY KEY (id),
    FOREIGN KEY (metadata_id) REFERENCES video_metadata(id)
);

CREATE TABLE video_impression (
    id long NOT NULL AUTO_INCREMENT,
    metadata_id long NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted NUMERIC(1),
    PRIMARY KEY (id),
    FOREIGN KEY (metadata_id) REFERENCES video_metadata(id)
);

CREATE TABLE video_views (
    id long NOT NULL AUTO_INCREMENT,
    content_id long NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted NUMERIC(1),
    PRIMARY KEY (id),
    FOREIGN KEY (content_id) REFERENCES video_content(id)
);