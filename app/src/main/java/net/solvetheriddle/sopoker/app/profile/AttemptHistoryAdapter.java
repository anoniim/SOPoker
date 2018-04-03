package net.solvetheriddle.sopoker.app.profile;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.solvetheriddle.sopoker.R;
import net.solvetheriddle.sopoker.network.model.Attempt;
import net.solvetheriddle.sopoker.network.model.AttemptStatus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class AttemptHistoryAdapter extends RecyclerView.Adapter<AttemptHistoryAdapter.AttemptHistoryViewHolder> {

    private final LayoutInflater mInflater;
    private List<Attempt> mAttempts;

    public AttemptHistoryAdapter(final Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AttemptHistoryAdapter.AttemptHistoryViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
            final int viewType) {
        View attemptItemView = mInflater.inflate(R.layout.attempt_item, parent, false);
        return new AttemptHistoryViewHolder(attemptItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final AttemptHistoryAdapter.AttemptHistoryViewHolder holder,
            final int position) {
        if (mAttempts != null) {
            final Attempt attempt = mAttempts.get(position);
            holder.attemptTime.setText(attempt.getTimestamp().toString());
            setBackground(
                    holder.attemptTime,
                    attempt.getStatus() == AttemptStatus.SUCCESS ? android.R.color.transparent : R.color.error);
        }
    }

    private void setBackground(final TextView view, final @ColorRes int color) {
        view.setBackgroundColor(ContextCompat.getColor(view.getContext(), color));
    }

    @Override
    public int getItemCount() {
        if (mAttempts != null) {
            return mAttempts.size();
        } else {
            return 0;
        }
    }

    public void setAttempts(List<Attempt> attempts) {
        mAttempts = attempts;
        notifyDataSetChanged();
    }

    class AttemptHistoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.attempt_time)
        TextView attemptTime;

        AttemptHistoryViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
