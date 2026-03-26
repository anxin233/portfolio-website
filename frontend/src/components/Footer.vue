<template>
  <footer class="footer">
    <div class="footer-glow"></div>
    <div class="container footer-inner">
      <div class="footer-left">
        <div class="footer-logo">
          <span class="bracket">&lt;</span>Dev<span class="bracket">/&gt;</span>
        </div>
        <p class="footer-desc">全栈工程师 · 前端架构 · Java后端</p>
      </div>

      <div class="footer-links">
        <a href="https://github.com/anxin233" target="_blank" rel="noopener" class="footer-link">
          <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor"><path d="M12 0C5.37 0 0 5.37 0 12c0 5.31 3.435 9.795 8.205 11.385.6.105.825-.255.825-.57 0-.285-.015-1.23-.015-2.235-3.015.555-3.795-.735-4.035-1.41-.135-.345-.72-1.41-1.23-1.695-.42-.225-1.02-.78-.015-.795.945-.015 1.62.87 1.845 1.23 1.08 1.815 2.805 1.305 3.495.99.105-.78.42-1.305.765-1.605-2.67-.3-5.46-1.335-5.46-5.925 0-1.305.465-2.385 1.23-3.225-.12-.3-.54-1.53.12-3.18 0 0 1.005-.315 3.3 1.23.96-.27 1.98-.405 3-.405s2.04.135 3 .405c2.295-1.56 3.3-1.23 3.3-1.23.66 1.65.24 2.88.12 3.18.765.84 1.23 1.905 1.23 3.225 0 4.605-2.805 5.625-5.475 5.925.435.375.81 1.095.81 2.22 0 1.605-.015 2.895-.015 3.3 0 .315.225.69.825.57A12.02 12.02 0 0024 12c0-6.63-5.37-12-12-12z"/></svg>
          GitHub
        </a>
        <a href="mailto:anidenen@outlook.com" class="footer-link">
          <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" stroke-width="2"><rect x="2" y="4" width="20" height="16" rx="2"/><path d="m2 4 10 8 10-8"/></svg>
          邮箱
        </a>
      </div>

      <div class="footer-bottom">
        <p>&copy; {{ new Date().getFullYear() }} DevPortfolio. Built with Vue 3 + Spring Boot</p>
      </div>
    </div>
  </footer>
</template>

<style scoped>
.footer {
  position: relative;
  padding: 60px 0 30px;
  border-top: 1px solid var(--border-glass);
  overflow: hidden;
}

.footer-glow {
  position: absolute;
  top: -2px;
  left: 50%;
  transform: translateX(-50%);
  width: 400px;
  height: 2px;
  background: var(--gradient-main);
  filter: blur(8px);
}

.footer-inner {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 24px;
}

.footer-logo {
  font-family: var(--font-mono);
  font-size: 1.3rem;
  font-weight: 700;
  color: var(--text-primary);
}

.bracket {
  color: var(--accent-cyan);
}

.footer-desc {
  color: var(--text-secondary);
  font-size: 0.9rem;
  margin-top: 6px;
}

.footer-left {
  text-align: center;
}

.footer-links {
  display: flex;
  gap: 24px;
}

.footer-link {
  display: flex;
  align-items: center;
  gap: 8px;
  color: var(--text-secondary);
  font-size: 0.9rem;
  transition: color 0.3s;
}

.footer-link:hover {
  color: var(--accent-cyan);
}

.footer-bottom {
  padding-top: 24px;
  border-top: 1px solid var(--border-glass);
  width: 100%;
  text-align: center;
}

.footer-bottom p {
  font-size: 0.8rem;
  color: var(--text-muted);
}
</style>
