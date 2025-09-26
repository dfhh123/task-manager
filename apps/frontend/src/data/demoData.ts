// Демо данные для планировщика задач

export const demoTasks = [
  {
    id: '1',
    title: 'Изучить модульную архитектуру фронтенда',
    description: 'Изучить современные подходы к созданию модульного фронтенда, включая микрофронтенды, модульные системы и компонентные библиотеки.',
    completed: false,
    createdAt: new Date(Date.now() - 2 * 24 * 60 * 60 * 1000).toISOString(), // 2 дня назад
    updatedAt: new Date(Date.now() - 2 * 24 * 60 * 60 * 1000).toISOString(),
    userId: '1',
  },
  {
    id: '2',
    title: 'Создать план разработки проекта',
    description: 'Составить детальный план разработки с указанием этапов, временных рамок и ответственных.',
    completed: true,
    createdAt: new Date(Date.now() - 5 * 24 * 60 * 60 * 1000).toISOString(), // 5 дней назад
    updatedAt: new Date(Date.now() - 1 * 24 * 60 * 60 * 1000).toISOString(), // 1 день назад
    userId: '1',
  },
  {
    id: '3',
    title: 'Настроить CI/CD pipeline',
    description: 'Настроить автоматическую сборку, тестирование и деплой приложения.',
    completed: false,
    createdAt: new Date(Date.now() - 3 * 24 * 60 * 60 * 1000).toISOString(), // 3 дня назад
    updatedAt: new Date(Date.now() - 3 * 24 * 60 * 60 * 1000).toISOString(),
    userId: '1',
  },
  {
    id: '4',
    title: 'Провести code review',
    description: 'Провести ревью кода и исправить найденные проблемы.',
    completed: false,
    createdAt: new Date(Date.now() - 1 * 24 * 60 * 60 * 1000).toISOString(), // 1 день назад
    updatedAt: new Date(Date.now() - 1 * 24 * 60 * 60 * 1000).toISOString(),
    userId: '1',
  },
  {
    id: '5',
    title: 'Написать документацию',
    description: 'Создать подробную документацию по API и пользовательскому интерфейсу.',
    completed: true,
    createdAt: new Date(Date.now() - 7 * 24 * 60 * 60 * 1000).toISOString(), // 7 дней назад
    updatedAt: new Date(Date.now() - 2 * 24 * 60 * 60 * 1000).toISOString(), // 2 дня назад
    userId: '1',
  },
];

export const demoUser = {
  id: '1',
  email: 'demo@example.com',
  name: 'Demo User',
  createdAt: new Date(Date.now() - 10 * 24 * 60 * 60 * 1000).toISOString(), // 10 дней назад
};
