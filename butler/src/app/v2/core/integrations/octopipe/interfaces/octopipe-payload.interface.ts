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

export enum ClusterProviderEnum {
  EKS = 'EKS',
  GENERIC = 'GENERIC',
  DEFAULT = 'DEFAULT'
}

export interface IEKSClusterConfig {
  provider: ClusterProviderEnum.EKS
  awsSID: string
  awsSecret: string
  awsRegion: string
  awsClusterName: string
}

export interface IGenericClusterConfig {
  provider: ClusterProviderEnum.GENERIC
  clientCertificate: string
  clientKey: string
  caData: string
  host: string
}
