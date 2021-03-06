/*
 * Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { MigrationInterface, QueryRunner } from 'typeorm'

export class CreateV2Executions20200715115000 implements MigrationInterface {

  public async up(queryRunner: QueryRunner) : Promise<void> {
    await queryRunner.query(`
      CREATE TABLE "public"."v2executions" (
        "id" uuid DEFAULT gen_random_uuid () NOT NULL,
        "type" Character Varying NOT NULL,
        "deployment_id" uuid NOT NULL,
        "incoming_circle_id" uuid,
        "status" Character Varying DEFAULT 'CREATED' NOT NULL,
        "notification_status" Character Varying DEFAULT 'NOT_SENT' NOT NULL,
        "created_at" timestamp without time zone DEFAULT now() NOT NULL,
        "finished_at" timestamp without time zone,
        PRIMARY KEY ( "id" ),
        CONSTRAINT "unique_executions_id" UNIQUE( "id" ),
        CONSTRAINT "fk_v2executions_v2deployments" FOREIGN KEY ( "deployment_id" ) REFERENCES "public"."v2deployments" ( "id" )
      );
      CREATE INDEX "index_deployments_id1" ON "public"."v2executions" USING btree( "deployment_id" );
      CREATE INDEX "index_created_at_id_v2executions" on "public"."v2executions" (created_at, id);
      `)
  }

  public async down(queryRunner: QueryRunner) : Promise<void> {
    await queryRunner.dropTable('v2executions')
  }
}
