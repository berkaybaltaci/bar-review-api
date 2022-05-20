CREATE UNIQUE INDEX IX_pk_comment ON comment (id);

CREATE UNIQUE INDEX "IX_pk_review" ON review (id);

CREATE UNIQUE INDEX IX_pk_user ON "user" (id);

ALTER TABLE "user"
    DROP COLUMN password;