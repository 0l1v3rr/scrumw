<script>
    let isError = false;
    let errorMsg;

    const handleError = (errMsg) => {
        errorMsg = errMsg;
        isError = true;
    };

    let username;
    let password;

    const handleFormSubmit = async () => {
        if(username == "" || !username) {
            handleError("Please provide a username.");
            return;
        }

        if(password == "" || !password) {
            handleError("Please provide a valid password.");
            return;
        }

        const loginUser = {
			username: username,
			password: password
		};

		const res = await fetch('http://localhost:8080/api/v1/users/login', {
			method: 'POST',
			body: JSON.stringify(loginUser),
			headers: {
				"Content-Type": "application/json"
			}
		});

        const resJson = await res.json();
        if(res.status == 200) {
			window.location.replace("/");

            let now = new Date();
            let time = now.getTime();
            let expireTime = time + 24*60*60*30*1000;
            now.setTime(expireTime);
            document.cookie = `token=${resJson.token}; expires=${now.toUTCString()}; path=/`;
            localStorage.setItem('username', resJson.username);

            return;
		}

        handleError(resJson.message);
    };
</script>

<svelte:head>
    <title>Log In | scrumw</title>
</svelte:head>

<main class="main">
    <div class="form">
        <div class="header">
            <div class="title">Log In to scrumw</div>
        </div>
    
        <div class="section-divider"></div>
    
        <form on:submit|preventDefault={handleFormSubmit}>
            <div class="form-section">
                <label for="scrumw-username">Username</label>
                <input type="text" class="input" id="scrumw-username" bind:value={username} required>
            </div>
        
            <div class="form-section mt-1">
                <label for="scrumw-password">Password</label>
                <input type="password" class="input" id="scrumw-password" bind:value={password} required>
            </div>
        
            <div class="section-divider"></div>
    
            <div class="register">
                Need an account? Register <a href="/register">here</a>!
            </div>
    
            <div class="section-divider"></div>

            {#if isError}
                <div class="error">
                    <div class="error-msg">{errorMsg}</div>
                    <div class="section-divider"></div>
                </div>
            {/if}
        
            <button type="submit" class="btn btn-primary">Log In</button>
        </form>
    </div>
</main>

<style>
    .main {
        width: 100vw;
        height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    .form {
        width: 40vw;
    }
    .header {
        margin-bottom: 1rem;
    }
    .title {
        color: var(--text-color-primary);
        font-size: 2rem;
    }
    .section-divider {
        width: 100%;
        height: 1px;
        background-color: var(--border-color);
        margin-block: 1rem;
    }
    .input {
        background-color: var(--background-primary);
        color: var(--text-color-primary);
        padding: .5rem 1rem;
        border-radius: .4rem;
        border: 1px solid var(--border-color);
        font-size: .9rem;
    }
    .input:focus {
        border: 1px solid var(--color-primary);
    }
    .form-section {
        display: flex;
        flex-direction: column;
        gap: .5rem;
    }
    .mt-1 {
        margin-top: 1rem;
    }
    .register {
        color: var(--text-color-secondary);
        font-size: .95rem;
    }
    .register > a {
        color: var(--link-color);
        cursor: pointer;
        transition: .3s ease-in-out;
    }
    .register > a:hover {
        color: var(--color-primary-light);
    }
    .error-msg {
        border: 1px solid var(--color-danger);
        color: var(--color-danger-light);
        border-radius: .5rem;
        padding: .5rem 1rem;
    }
    @media screen and (max-width: 576px) {
        .form {
            width: 100vw;
            padding: 1rem;
        }
    }
</style>