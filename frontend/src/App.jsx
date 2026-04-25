import { startTransition, useEffect, useMemo, useState } from 'react';
import { Virtuoso } from 'react-virtuoso';

const HISTORY_API_BASE_URL =
  import.meta.env.VITE_HISTORY_API_BASE_URL || 'http://localhost:8081';
const HISTORY_API_REQUEST_BASE_URL =
  import.meta.env.VITE_HISTORY_API_REQUEST_BASE_URL || '/history-api';
const APP_PLUG_BASE_URL =
  import.meta.env.VITE_APP_PLUG_BASE_URL || 'http://localhost:8080';

const dateFormatter = new Intl.DateTimeFormat('ru-RU', {
  dateStyle: 'short',
  timeStyle: 'medium',
});

function formatDateFast(value) {
  if (!value) {
    return '-';
  }

  return dateFormatter.format(new Date(value));
}

function App() {
  const [payments, setPayments] = useState([]);
  const [lastImportedPayment, setLastImportedPayment] = useState(null);
  const [loadingHistory, setLoadingHistory] = useState(true);
  const [importing, setImporting] = useState(false);
  const [error, setError] = useState('');
  const [lastUpdatedAt, setLastUpdatedAt] = useState('');
  const preparedPayments = useMemo(
    () =>
      payments.map((payment) => ({
        ...payment,
        createdAtLabel: formatDateFast(payment.createdAt),
        updatedAtLabel: formatDateFast(payment.updatedAt),
      })),
    [payments],
  );

  async function loadHistory() {
    setLoadingHistory(true);
    setError('');

    try {
      const response = await fetch(`${HISTORY_API_REQUEST_BASE_URL}/api/v1/payment`);
      if (!response.ok) {
        throw new Error(`Не удалось загрузить историю: ${response.status}`);
      }

      const payload = await response.json();
      startTransition(() => {
        setPayments(payload);
        setLastUpdatedAt(new Date().toLocaleTimeString('ru-RU'));
      });
    } catch (requestError) {
      setError(requestError.message);
    } finally {
      setLoadingHistory(false);
    }
  }

  async function importPayment() {
    setImporting(true);
    setError('');

    try {
      const response = await fetch(`${HISTORY_API_REQUEST_BASE_URL}/api/v1/payment`, {
        method: 'POST',
      });

      if (!response.ok) {
        throw new Error(`Не удалось сохранить платёж: ${response.status}`);
      }

      const payload = await response.json();
      startTransition(() => {
        setLastImportedPayment(payload);
      });

      await loadHistory();
    } catch (requestError) {
      setError(requestError.message);
    } finally {
      setImporting(false);
    }
  }

  useEffect(() => {
    loadHistory();
  }, []);

  return (
    <main className="page-shell">
      <section className="hero">
        <div className="hero__copy">
          <p className="eyebrow">DIT Console</p>
          <h1>История платежей и загрузка данных из app-plug</h1>
          <p className="lead">
            Одностраничный frontend для ручной проверки интеграции, истории платежей и
            технических URL проекта.
          </p>
        </div>

        <div className="hero__panel">
          <button className="primary-button" onClick={importPayment} disabled={importing}>
            {importing ? 'Импортируем...' : 'Импортировать новый платёж'}
          </button>
          <button className="ghost-button" onClick={loadHistory} disabled={loadingHistory}>
            {loadingHistory ? 'Обновляем...' : 'Обновить историю'}
          </button>
          <p className="caption">
            Последнее обновление списка: <strong>{lastUpdatedAt || 'ещё не было'}</strong>
          </p>
        </div>
      </section>

      <section className="summary-grid">
        <article className="summary-card">
          <span className="summary-card__label">Записей в текущем ответе</span>
          <strong className="summary-card__value">{payments.length}</strong>
        </article>
        <article className="summary-card">
          <span className="summary-card__label">Последний импортированный платёж</span>
          <strong className="summary-card__value">
            {lastImportedPayment?.id || 'ещё не импортировали'}
          </strong>
        </article>
        <article className="summary-card">
          <span className="summary-card__label">History API</span>
          <strong className="summary-card__value">{HISTORY_API_BASE_URL}</strong>
        </article>
      </section>

      <section className="content-grid">
        <article className="panel">
          <div className="panel__header">
            <h2>История платежей</h2>
            <span className="panel__tag">GET /api/v1/payment</span>
          </div>

          {error ? <p className="error-banner">{error}</p> : null}

          {loadingHistory ? (
            <p className="empty-state">Загрузка истории...</p>
          ) : payments.length === 0 ? (
            <p className="empty-state">История пока пуста.</p>
          ) : (
            <div className="payment-list-virtual">
              <Virtuoso
                data={preparedPayments}
                overscan={240}
                itemContent={(_, payment) => (
                  <article key={payment.id} className="payment-card">
                    <div className="payment-card__meta">
                      <span className="status-pill">{payment.statusCode}</span>
                      <span>{payment.currencyCode}</span>
                    </div>
                    <h3>{payment.amount}</h3>
                    <p>{payment.description || 'Без комментария'}</p>
                    <dl className="payment-card__details">
                      <div>
                        <dt>ID</dt>
                        <dd>{payment.id}</dd>
                      </div>
                      <div>
                        <dt>External ID</dt>
                        <dd>{payment.externalId}</dd>
                      </div>
                      <div>
                        <dt>Создан</dt>
                        <dd>{payment.createdAtLabel}</dd>
                      </div>
                      <div>
                        <dt>Обновлён</dt>
                        <dd>{payment.updatedAtLabel}</dd>
                      </div>
                    </dl>
                  </article>
                )}
              />
            </div>
          )}
        </article>

        <aside className="panel panel--sidebar">
          <div className="panel__header">
            <h2>Ссылки проекта</h2>
            <span className="panel__tag">Быстрый доступ</span>
          </div>

          <div className="link-list">
            <a href={`${HISTORY_API_BASE_URL}/swagger-ui.html`} target="_blank" rel="noreferrer">
              Swagger payment-history-service
            </a>
            <a href={`${APP_PLUG_BASE_URL}/swagger-ui.html`} target="_blank" rel="noreferrer">
              Swagger app-plug
            </a>
            <a
              href={`${HISTORY_API_BASE_URL}/actuator/prometheus`}
              target="_blank"
              rel="noreferrer"
            >
              Метрики payment-history-service
            </a>
            <a href={`${APP_PLUG_BASE_URL}/actuator/prometheus`} target="_blank" rel="noreferrer">
              Метрики app-plug
            </a>
            <a href="http://localhost:9090" target="_blank" rel="noreferrer">
              Prometheus
            </a>
            <a href="http://localhost:3000" target="_blank" rel="noreferrer">
              Grafana
            </a>
          </div>

          <div className="sidebar-note">
            <h3>Что умеет экран</h3>
            <ul>
              <li>Импортировать новый платёж из `app-plug`</li>
              <li>Показывать последние 10 записей из истории</li>
              <li>Давать быстрые ссылки на Swagger и метрики</li>
            </ul>
          </div>
        </aside>
      </section>
    </main>
  );
}

export default App;
