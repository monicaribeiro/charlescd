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

import styled from 'styled-components';
import ComponentIcon from 'core/components/Icon';
import { slideInRight } from 'core/assets/style/animate';
import LayerComponent from 'core/components/Layer';
import Button from 'core/components/Button';
import { HEADINGS_FONT_SIZE } from 'core/components/Text/enums';

const Icon = styled(ComponentIcon)`
  animation: ${slideInRight} 1s forwards;
`;

const Layer = styled(LayerComponent)`
  margin-left: 40px;
`;

const ButtonAdd = styled(Button.Rounded)`
  margin-top: 15px;
  margin-bottom: 15px;

  height: 40px;
  padding: 13px 25px;

  span {
    font-weight: normal;
    font-size: ${HEADINGS_FONT_SIZE.h6};
  }
`;

export default {
  Layer,
  Icon,
  ButtonAdd
};
