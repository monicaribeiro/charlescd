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

import React from 'react';
import { render, screen } from 'unit-test/testUtils';
import { act } from 'react-dom/test-utils';
import { FetchMock } from 'jest-fetch-mock';
import { dark as cardBoardTheme } from 'core/assets/themes/card/board';
import { propsAction, propsFeature } from './mocks'
import CardBoard from '..'
import userEvent from '@testing-library/user-event';

beforeEach(() => {
  (fetch as FetchMock).resetMocks();
});

test('render Card type.FEATURE', async () => {
  render(<CardBoard {...propsFeature} />);

  const Card = await screen.findByTestId(`card-board-${propsFeature.card.id}`);
  expect(Card).toBeInTheDocument();
  expect(Card).toHaveStyle(`background-color: ${cardBoardTheme.FEATURE.background};`);
});

test('render Card type.ACTION', async () => {
  render(<CardBoard {...propsAction} />);

  const Card = await screen.findByTestId(`card-board-${propsAction.card.id}`);
  expect(Card).toBeInTheDocument();
  expect(Card).toHaveStyle(`background-color: ${cardBoardTheme.ACTION.background};`);
});

test('render Card type.FEATURE and open view', async () => {
  render(<CardBoard {...propsFeature} />);

  const Card = await screen.findByTestId(`card-board-${propsFeature.card.id}`);
  expect(Card).toBeInTheDocument();

  await act(async () => userEvent.click(Card));

  const ModalView = await screen.findByTestId("modal-default");
  expect(ModalView).toBeInTheDocument();
});

test('render Card type.ACTION and open view', async () => {
  render(<CardBoard {...propsAction} />);

  const Card = await screen.findByTestId(`card-board-${propsAction.card.id}`);
  expect(Card).toBeInTheDocument();

  await act(async () => userEvent.click(Card));

  const ModalView = await screen.findByTestId("modal-default");
  expect(ModalView).toBeInTheDocument();
});

test('render Card type.ACTION and open dropdown options', async () => {
  render(<CardBoard {...propsAction} />);

  const Card = await screen.findByTestId(`card-board-${propsAction.card.id}`);
  expect(Card).toBeInTheDocument();

  const DropdownTrigger = await screen.findByTestId("icon-vertical-dots");
  await act(async () => userEvent.click(DropdownTrigger));

  const DropdownOptions = await screen.findByTestId("dropdown-actions");
  expect(DropdownOptions).toBeInTheDocument();
});