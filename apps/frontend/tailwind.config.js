/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          50: '#eff6ff',
          100: '#dbeafe',
          200: '#bfdbfe',
          300: '#93c5fd',
          400: '#60a5fa',
          500: '#3b82f6',
          600: '#2563eb',
          700: '#1d4ed8',
          800: '#1e40af',
          900: '#1e3a8a',
        },
        pink: {
          50: '#fdf2f8',
          100: '#fce7f3',
          200: '#fbcfe8',
          300: '#f9a8d4',
          400: '#f472b6',
          500: '#ec4899',
          600: '#db2777',
          700: '#be185d',
          800: '#9d174d',
          900: '#831843',
        },
        accent: {
          50: '#fdf2f8',
          100: '#fce7f3',
          200: '#fbcfe8',
          300: '#f9a8d4',
          400: '#f472b6',
          500: '#ec4899',
          600: '#db2777',
          700: '#be185d',
          800: '#9d174d',
          900: '#831843',
        },
      },
      backgroundImage: {
        'gradient-pink': 'linear-gradient(135deg, #ec4899 0%, #db2777 100%)',
        'gradient-pink-blue': 'linear-gradient(135deg, #ec4899 0%, #3b82f6 100%)',
        'gradient-pink-purple': 'linear-gradient(135deg, #ec4899 0%, #8b5cf6 100%)',
        'gradient-soft-pink': 'linear-gradient(135deg, #fce7f3 0%, #fbcfe8 100%)',
        'gradient-radial-pink': 'radial-gradient(ellipse at center, #ec4899 0%, #db2777 100%)',
      },
    },
  },
  plugins: [],
} 