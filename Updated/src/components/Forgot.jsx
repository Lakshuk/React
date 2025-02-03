// import React, { useState } from "react";
// import { useNavigate } from "react-router-dom";

// const ForgotPassword = () => {
//   const [email, setEmail] = useState("");
//   const [newPassword, setNewPassword] = useState("");
//   const [confirmPassword, setConfirmPassword] = useState("");
//   const [step, setStep] = useState(1); // Step 1: Enter Email, Step 2: Reset Password
//   const [error, setError] = useState("");
//   const [success, setSuccess] = useState("");

//   const navigate = useNavigate();

//   const handleEmailSubmit = (e) => {
//     e.preventDefault();

//     // Mock email check (Replace this with an actual API call)
//     const storedEmail = "test@example.com"; 
//     if (email === storedEmail) {
//       setStep(2);
//       setError("");
//     } else {
//       setError("Email not found! Please enter a registered email.");
//     }
//   };

//   const handlePasswordReset = (e) => {
//     e.preventDefault();

//     if (newPassword !== confirmPassword) {
//       setError("Passwords do not match!");
//       return;
//     }

//     // Mock password update (Replace this with an API call)
//     setSuccess("Password has been reset successfully!");
//     setError("");

//     setTimeout(() => {
//       navigate("/"); // Redirect to login page
//     }, 2000);           
//   };

//   return (
//     <div className="forgot-password-container">
//       <h2>Forgot Password</h2>

//       {step === 1 ? (
//         <form onSubmit={handleEmailSubmit}>
//           <label>Email:</label>
//           <input
//             type="email"
//             placeholder="Enter your email"
//             value={email}
//             onChange={(e) => setEmail(e.target.value)}
//             required
//           />
//           <button type="submit">Submit</button>
//         </form>
//       ) : (
//         <form onSubmit={handlePasswordReset}>
//           <label>New Password:</label>
//           <input
//             type="password"
//             placeholder="Enter new password"
//             value={newPassword}
//             onChange={(e) => setNewPassword(e.target.value)}
//             required
//           />
//           <label>Confirm Password:</label>
//           <input
//             type="password"
//             placeholder="Confirm new password"
//             value={confirmPassword}
//             onChange={(e) => setConfirmPassword(e.target.value)}
//             required
//           />
//           <button type="submit">Reset Password</button>
//         </form>
//       )}

//       {error && <p className="error">{error}</p>}
//       {success && <p className="success">{success}</p>}
//     </div>
//   );
// };

// export default ForgotPassword;




















// import React, { useState } from "react";
// import { useAuth } from "../context/AuthContext";

// const ForgotPassword = () => {
//   const { forgotPassword } = useAuth();
//   const [email, setEmail] = useState("");
//   const [message, setMessage] = useState("");

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     const result = await forgotPassword(email);
//     setMessage(result.message);
//   };

//   return (
//     <div>
//       <h2>Forgot Password</h2>
//       <form onSubmit={handleSubmit}>
//         <label>Email:</label>
//         <input
//           type="email"
//           value={email}
//           onChange={(e) => setEmail(e.target.value)}
//           required
//         />
//         <button type="submit">Request Reset Token</button>
//       </form>
//       {message && <p>{message}</p>}
//     </div>
//   );
// };

// export default ForgotPassword;





import React, { useState } from "react";
import { useAuth } from "../context/AuthContext";
import axios from "axios";

const ForgotPassword = () => {
  const { forgotPassword } = useAuth();
  const [email, setEmail] = useState("");
  const [message, setMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try{
      const result = await axios.post("http://localhost:8080/student/forgot-password",
        { email });
      setMessage(result.message);
    }
    catch(error){
      setMessage("User Not Found");
    }
    
  };

  return (
    <div>
      <h2>Forgot Password</h2>
      <form onSubmit={handleSubmit}>
        <label>Email:</label>
        <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <button type="submit">Request Reset Token</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default ForgotPassword;

