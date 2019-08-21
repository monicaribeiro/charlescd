import { Module } from '@nestjs/common'
import { DeploymentsController } from './controller'
import { IntegrationsModule } from '../../core/integrations/integrations.module'
import { ServicesModule } from '../../core/services/services.module'
import { TypeOrmModule } from '@nestjs/typeorm'
import { ComponentDeploymentEntity, DeploymentEntity, ModuleDeploymentEntity } from './entity'
import { ComponentEntity, ModuleEntity } from '../modules/entity'
import { LogsModule } from '../../core/logs/logs.module'
import { QueuedDeploymentsRepository } from './repository'
import {
  DeploymentsService,
  QueuedDeploymentsService,
  PipelineProcessingService,
  PipelineDeploymentService
} from './service'

@Module({
  imports: [
    IntegrationsModule,
    ServicesModule,
    LogsModule,
    TypeOrmModule.forFeature([
      DeploymentEntity,
      ModuleDeploymentEntity,
      ComponentDeploymentEntity,
      ModuleEntity,
      ComponentEntity,
      QueuedDeploymentsRepository
    ])
  ],
  controllers: [DeploymentsController],
  providers: [
    DeploymentsService,
    QueuedDeploymentsService,
    PipelineProcessingService,
    PipelineDeploymentService
  ],
  exports: [
    DeploymentsService,
    QueuedDeploymentsService,
    PipelineProcessingService,
    PipelineDeploymentService
  ]
})
export class DeploymentsModule {}
