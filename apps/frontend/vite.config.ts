import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    host: true,
  },
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
      '@/modules': path.resolve(__dirname, './src/modules'),
      '@/components': path.resolve(__dirname, './src/components'),
      '@/shared': path.resolve(__dirname, './src/shared'),
    },
  },
  build: {
    rollupOptions: {
      output: {
        manualChunks: {
          // Разделяем код по модулям
          'user-module': ['./src/modules/user'],
          'task-module': ['./src/modules/task'],
          'analytics-module': ['./src/modules/analytics'],
          'events-module': ['./src/modules/events'],
          // Общие библиотеки
          'vendor': ['react', 'react-dom', 'react-router-dom'],
        },
      },
    },
  },
  // Поддержка динамических импортов для lazy loading
  optimizeDeps: {
    include: ['react', 'react-dom', 'react-router-dom'],
  },
}) 