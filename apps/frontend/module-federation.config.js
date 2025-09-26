// Конфигурация Module Federation для микрофронтендов

const ModuleFederationPlugin = require('@module-federation/webpack');

module.exports = {
  mode: 'development',
  devServer: {
    port: 3000,
  },
  plugins: [
    new ModuleFederationPlugin({
      name: 'host',
      remotes: {
        // Подключаем удаленные модули
        userModule: 'userModule@http://localhost:3001/remoteEntry.js',
        taskModule: 'taskModule@http://localhost:3002/remoteEntry.js',
        analyticsModule: 'analyticsModule@http://localhost:3003/remoteEntry.js',
      },
      shared: {
        // Общие зависимости
        react: {
          singleton: true,
          requiredVersion: '^18.2.0',
        },
        'react-dom': {
          singleton: true,
          requiredVersion: '^18.2.0',
        },
        'react-router-dom': {
          singleton: true,
          requiredVersion: '^6.20.0',
        },
      },
    }),
  ],
};

// Пример конфигурации для отдельного модуля (например, user-module)
const userModuleConfig = {
  name: 'userModule',
  filename: 'remoteEntry.js',
  exposes: {
    './UserDashboard': './src/components/UserDashboard',
    './UserProfile': './src/components/UserProfile',
    './UserService': './src/services/UserService',
  },
  shared: {
    react: { singleton: true },
    'react-dom': { singleton: true },
  },
};
