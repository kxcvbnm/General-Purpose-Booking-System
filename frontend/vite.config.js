import { defineConfig } from 'vite'
import tailwindscss from '@tailwindcss/vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react(),
    tailwindscss(),
  ],
  server: {
    
    proxy: {
      "/api": {
        target: process.env.BACKEND_API || "http://localhost:8081",
        changeOrigin: true,
        secure: false,
      },
    },
  },
})
