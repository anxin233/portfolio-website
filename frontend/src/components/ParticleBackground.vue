<template>
  <canvas ref="canvasRef" class="particle-canvas"></canvas>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'

const canvasRef = ref<HTMLCanvasElement>()
let animationId: number
let particles: Particle[] = []
let mouse = { x: -1000, y: -1000 }

interface Particle {
  x: number
  y: number
  vx: number
  vy: number
  size: number
  opacity: number
  color: string
}

const colors = ['#00d4ff', '#7b2ff7', '#ff2d95', '#00e88f']

function createParticle(w: number, h: number): Particle {
  return {
    x: Math.random() * w,
    y: Math.random() * h,
    vx: (Math.random() - 0.5) * 0.5,
    vy: (Math.random() - 0.5) * 0.5,
    size: Math.random() * 2 + 0.5,
    opacity: Math.random() * 0.5 + 0.1,
    color: colors[Math.floor(Math.random() * colors.length)],
  }
}

function init() {
  const canvas = canvasRef.value
  if (!canvas) return
  const ctx = canvas.getContext('2d')
  if (!ctx) return

  function resize() {
    canvas!.width = window.innerWidth
    canvas!.height = window.innerHeight
  }

  resize()
  window.addEventListener('resize', resize)

  const count = Math.min(Math.floor((canvas.width * canvas.height) / 12000), 120)
  particles = Array.from({ length: count }, () => createParticle(canvas.width, canvas.height))

  function draw() {
    ctx!.clearRect(0, 0, canvas!.width, canvas!.height)

    for (let i = 0; i < particles.length; i++) {
      const p = particles[i]
      p.x += p.vx
      p.y += p.vy

      if (p.x < 0 || p.x > canvas!.width) p.vx *= -1
      if (p.y < 0 || p.y > canvas!.height) p.vy *= -1

      ctx!.beginPath()
      ctx!.arc(p.x, p.y, p.size, 0, Math.PI * 2)
      ctx!.fillStyle = p.color
      ctx!.globalAlpha = p.opacity
      ctx!.fill()

      for (let j = i + 1; j < particles.length; j++) {
        const p2 = particles[j]
        const dx = p.x - p2.x
        const dy = p.y - p2.y
        const dist = Math.sqrt(dx * dx + dy * dy)

        if (dist < 150) {
          ctx!.beginPath()
          ctx!.moveTo(p.x, p.y)
          ctx!.lineTo(p2.x, p2.y)
          ctx!.strokeStyle = p.color
          ctx!.globalAlpha = (1 - dist / 150) * 0.15
          ctx!.lineWidth = 0.5
          ctx!.stroke()
        }
      }

      const mdx = p.x - mouse.x
      const mdy = p.y - mouse.y
      const mDist = Math.sqrt(mdx * mdx + mdy * mdy)
      if (mDist < 200) {
        ctx!.beginPath()
        ctx!.moveTo(p.x, p.y)
        ctx!.lineTo(mouse.x, mouse.y)
        ctx!.strokeStyle = '#00d4ff'
        ctx!.globalAlpha = (1 - mDist / 200) * 0.4
        ctx!.lineWidth = 0.8
        ctx!.stroke()
      }
    }

    ctx!.globalAlpha = 1
    animationId = requestAnimationFrame(draw)
  }

  draw()
}

function handleMouseMove(e: MouseEvent) {
  mouse.x = e.clientX
  mouse.y = e.clientY
}

onMounted(() => {
  init()
  window.addEventListener('mousemove', handleMouseMove)
})

onUnmounted(() => {
  cancelAnimationFrame(animationId)
  window.removeEventListener('mousemove', handleMouseMove)
})
</script>

<style scoped>
.particle-canvas {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  z-index: 0;
}
</style>
