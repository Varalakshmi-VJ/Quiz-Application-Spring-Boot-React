import { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { getQuiz, submitQuiz } from '../api';

export default function Quiz() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [quiz, setQuiz] = useState(null);
  const [error, setError] = useState('');
  const [playerName, setPlayerName] = useState('');
  const [started, setStarted] = useState(false);
  const [current, setCurrent] = useState(0);
  const [answers, setAnswers] = useState({}); // { questionId: selectedOptionIndex }
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    getQuiz(id)
      .then(setQuiz)
      .catch((e) => setError(e.message));
  }, [id]);

  if (error && !quiz) return <p className="message error">{error} <Link to="/">Back</Link></p>;
  if (!quiz) return <p className="message">Loading quiz...</p>;

  const total = quiz.questions.length;
  const question = quiz.questions[current];
  const answeredCount = Object.keys(answers).length;
  const isLast = current === total - 1;

  const selectOption = (index) => setAnswers({ ...answers, [question.id]: index });

  const handleSubmit = async () => {
    setSubmitting(true);
    setError('');
    try {
      const result = await submitQuiz(id, { playerName, answers });
      navigate('/result', { state: { result } });
    } catch (e) {
      setError(e.message);
      setSubmitting(false);
    }
  };

  if (!started) {
    return (
      <div className="panel">
        <span className="badge">{quiz.topic}</span>
        <h1>{quiz.title}</h1>
        <p>{quiz.description}</p>
        <p><strong>{total}</strong> questions. Enter your name to begin.</p>
        <input
          className="input"
          type="text"
          placeholder="Your name"
          maxLength={50}
          value={playerName}
          onChange={(e) => setPlayerName(e.target.value)}
        />
        <button
          className="btn"
          disabled={!playerName.trim()}
          onClick={() => setStarted(true)}
        >
          Start quiz
        </button>
      </div>
    );
  }

  return (
    <div className="panel">
      <div className="progress-row">
        <span>Question {current + 1} of {total}</span>
        <span>{answeredCount} answered</span>
      </div>
      <div className="progress">
        <div className="progress-bar" style={{ width: `${((current + 1) / total) * 100}%` }} />
      </div>

      <h2 className="question">{question.text}</h2>
      <div className="options">
        {question.options.map((option, index) => (
          <button
            key={index}
            className={`option ${answers[question.id] === index ? 'selected' : ''}`}
            onClick={() => selectOption(index)}
          >
            <span className="option-letter">{String.fromCharCode(65 + index)}</span>
            {option}
          </button>
        ))}
      </div>

      {error && <p className="message error">{error}</p>}

      <div className="nav-row">
        <button className="btn secondary" disabled={current === 0} onClick={() => setCurrent(current - 1)}>
          Previous
        </button>
        {isLast ? (
          <button className="btn" disabled={submitting} onClick={handleSubmit}>
            {submitting ? 'Submitting...' : 'Submit'}
          </button>
        ) : (
          <button className="btn" onClick={() => setCurrent(current + 1)}>Next</button>
        )}
      </div>
      {isLast && answeredCount < total && (
        <p className="hint">You have {total - answeredCount} unanswered question(s). Unanswered questions score 0.</p>
      )}
    </div>
  );
}
